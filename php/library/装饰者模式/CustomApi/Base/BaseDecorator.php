<?php
/**
 * +-----------------------------------------------
 * User:  wangzhaorun
 * date:  2019/11/28
 *
 * 自定义接装饰器
 * +-----------------------------------------------
 */


namespace Common\CustomApi\Base;


abstract class BaseDecorator
{

    /**
     * @var array
     */
    public $condition = [];

    /**
     * 拦截器-根据自己的业务处理数据
     * @param $data
     * @return mixed
     */
    public abstract function handle(array $data): array;

    /**
     * 传递条件
     *
     * @param array $condition
     * @return array
     */
    public function setCondition($condition = [])
    {
        return $this->condition = $condition;
    }

}