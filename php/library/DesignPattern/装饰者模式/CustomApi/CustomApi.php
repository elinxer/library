<?php
/**
 * 自定义接口
 */

namespace App\HttpController;


use App\Service\CustomApiService;

class CustomApi extends Base
{

    public function __construct()
    {
        parent::__construct();
    }


    public function getItem()
    {

        $api_code = $this->input('api_code', '');
        if (empty($api_code)) {
            $this->handleError(0, 'api_code 不能为空！');
        }


        try {
            $service = CustomApiService::getInstance();

            //过滤查询条件
            $list_field = $service->getItemFieldsByApiCode($api_code);
            $condi = [];
            if (!empty($list_field)) {
                foreach ($list_field as $_field => $_item) {
                    if (!empty($query_value = $this->input($_field))) {
                        $condi[$_field] = $query_value;
                    }
                }
            }
            $data = $service = $service->getItemListdataOfDecoration($api_code, $condi);
        } catch (\Exception $e) {
            return $this->handleError(0, $e->getMessage());
        }
        return $this->success($data ?? []);
    }
}
