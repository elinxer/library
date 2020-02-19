<?php

namespace App\Service;


use App\Model\CustomApiModel;
use App\Utility\ApiTait;
use Common\CustomApi\Base\BaseDecorator;
use EasySwoole\Component\Singleton;

/**
 * 自定义API接口服务层
 */
class CustomApiService
{

    use  ApiTait;
    use Singleton;

    protected $app_id;

    private $_customapi_item = 'customapi_item_'; //记录数据
    private $_customapi_item_listdata = 'customapi_item_listdata_'; //缓存数据
    private $_customapi_item_listdata_index = 'customapi_item_listdata_index_';  //缓存主键


    /**
     * //事业部ID
     * @var integer
     */
    private $company_id = 0;


    public function __construct()
    {
        $this->company_id = $this->getCompanyId();
    }


    public function getItemByApiCode($api_code)
    {
        $data = [];

        $cache_obj = $this->redisCache();
        $item_cache_key = $this->_customapi_item . $data['id'];


        $model = CustomApiModel::getInstance();
        $data = $model->getApiData($this->company_id, $api_code);


        return $data;
    }


    /**
     * 根据api_code获取api配置字段
     * @param $api_code
     */
    public function getItemFieldsByApiCode($api_code)
    {
        $cache_obj = $this->redisCache();
        $item_cache_key = $this->_customapi_item . $api_code;
        $data = $cache_obj->get($item_cache_key);
        if (empty($data)) {
            $model = CustomApiModel::getInstance();
            $data = $model->getApiData($this->company_id, $api_code);
            unset($data['list_data']);
            $cache_obj->set($item_cache_key, \GuzzleHttp\json_encode($data));
        } else {
            $data = \GuzzleHttp\json_decode($data, true);
        }
        $list_field = $data['list_field'] ?? [];
        return $list_field;
    }

    /**
     * 获取装饰过后的API数据
     * @param $api_code
     * @param array $condition
     */
    public function getItemListdataOfDecoration($api_code, $condition = []): array
    {
        $data = $this->getItemListdata($api_code, $condition);

        //加载装饰器
        $decroator = \Common\CustomApi\DecoratorFactory::create($api_code);

        if (!($decroator instanceof BaseDecorator)) {
            throw new \Exception("API装饰器加装有误！");
        }

        $decroator->setCondition($condition);

        $data = $decroator->handle($data);
        return !empty($data) ? $data : [];
    }


    /**
     * 获取api_code的数据列表
     * @param $api_code
     * @param array $condition
     */
    public function getItemListdata($api_code, $condition = [])
    {
        $list_field = $this->getItemFieldsByApiCode($api_code); //字段
        //缓存主键
        $paramary_field = '';
        if (!empty($list_field)) {
            foreach ($list_field as $field => $item) {
                if (!empty($item['isparamarykey'])) {
                    $paramary_field = $field;
                }
            }
        } else {
            throw  new \Exception("该api_code[" . $api_code . "]字段列表为空，请检查相关配置");
        }

        $cache_obj = $this->redisCache();
        $listdata_cache_key = $this->_customapi_item_listdata . $api_code;

        if (!empty($condition)) {

            $paramary_value = '';
            foreach ($condition as $_field => $_value) {
                if ($paramary_field == $_field) {
                    //获取主键映射值
                    $paramary_value = $_value;
                }
            }

            $listdata_recordid = 0;
            if (!empty($paramary_value)) {
                $index_cache_key = $this->_customapi_item_listdata_index . $api_code;
                //获取映射记录id
                $listdata_recordid = $cache_obj->hGet($index_cache_key, $paramary_value);
            }
        }

        $listdata = [];
        if (!empty($listdata_recordid)) {
            //有主键记录的直接查询
            $listdata = $cache_obj->hGet($listdata_cache_key, $listdata_recordid);
            if (!empty($listdata)) {
                $listdata = \GuzzleHttp\json_decode($listdata, true);
                return [$listdata_recordid => $listdata];
            }
        }

        $lists = $cache_obj->hGetAll($listdata_cache_key);
        if (empty($lists)) {
            $model = CustomApiModel::getInstance();
            $data = $model->getApiData($this->company_id, $api_code);
            if (!empty($data) && !empty($data['list_data'])) {
                //写缓存
                foreach ($data['list_data'] as $row_key => $item) {
                    $item = \GuzzleHttp\json_encode($item);
                    $lists[$row_key] = $item;
                    $cache_obj->hSet($listdata_cache_key, $row_key, $item);
                }
            }
        }
        if (!empty($lists)) {
            foreach ($lists as $rid => $item) {
                $item = \GuzzleHttp\json_decode($item, true);
                //条件过滤
                if (!empty($condition)) {
                    $pass = [];
                    foreach ($condition as $_field => $_value) {
                        if ($item[$_field] == $_value) {
                            $pass[] = 1;
                        }
                    }
                    //不符合条件的退出
                    if (count($condition) != array_sum($pass)) {
                        continue;
                    }
                }
                $listdata[$rid] = $item;
            }
        }
        return $listdata;
    }

    /**
     * 更新记录缓存及记录里的listdata缓存
     * @param $api_code
     * @throws \Exception
     */
    public function itemAndListdataCache($api_code)
    {


        try {
            $model = CustomApiModel::getInstance();
            $data = $model->getApiData($this->company_id, $api_code);

        } catch (\Exception $e) {
            throw new \Exception("API数据json格式数据有误");
        }
        if (empty($data)) {
            throw new \Exception("数据记录为空！");
        }

        //记录数据缓存
        $cache_obj = $this->redisCache();
        $item_cache_key = $this->_customapi_item . $data['api_code'];
        $item_data = $data;
        unset($item_data['list_data']);
        $cache_obj->set($item_cache_key, \GuzzleHttp\json_encode($item_data));

        //记录里列表数据缓存
        $list_data = $data['list_data'] ?? [];

        //缓存主键
        $paramary_field = '';
        if (!empty($data['list_field'])) {
            foreach ($data['list_field'] as $field => $item) {
                if (!empty($item['isparamarykey'])) {
                    $paramary_field = $field;
                }
            }
        }

        if (!empty($paramary_field) && !empty($list_data)) {
            $list_data_index = [];
            foreach ($list_data as $key => $item) {
                $list_data_index[$item[$paramary_field]] = $key;
            }
            //主键值为key,存储记录ID
            $index_cache_key = $this->_customapi_item_listdata_index . $data['api_code'];
            //缓存清掉重建
            $cache_obj->del($index_cache_key);
            foreach ($list_data_index as $paramary_value => $row_key) {
                $cache_obj->hSet($index_cache_key, $paramary_value, $row_key);
            }
        }

        if (!empty($list_data)) {
            //缓存列表数据
            $listdata_cache_key = $this->_customapi_item_listdata . $data['api_code'];

            //缓存清掉重建
            $cache_obj->del($listdata_cache_key);
            foreach ($list_data as $row_key => $item) {
                $cache_obj->hSet($listdata_cache_key, $row_key, \GuzzleHttp\json_encode($item));
            }
        }
        return true;
    }

}