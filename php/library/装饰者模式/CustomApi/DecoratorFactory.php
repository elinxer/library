<?php
/**
 * +-----------------------------------------------
 * User:  wangzhaorun
 * date:  2019/11/28
 *  装饰器工厂类
 * +-----------------------------------------------
 */


namespace Common\CustomApi;


use Common\CustomApi\Base\BaseDecorator;

class DecoratorFactory
{

    private static $_obj = null;

    /**
     * 创建装饰器
     * @param string $api_code
     * @return BaseDecorator
     * @throws \ReflectionException
     */
    public static function create(string $api_code): BaseDecorator
    {
        $class_lists = [
            'Common\CustomApi\Decoration\Decorator_' . $api_code,  //根据api_code为名的自定义装饰器
            'Common\CustomApi\Decoration\DecoratorCommon',
        ];

        //按顺序取发货对象类
        $class_name = '';
        foreach ($class_lists as $val) {
            if (class_exists($val)) {
                $class_name = $val;
                break;
            }
        }
        $key = md5($class_name);
        if (isset(self::$_obj[$key]) && !empty(self::$_obj[$key])) {
            return self::$_obj[$key];
        }
        $ref = new \ReflectionClass($class_name);
        $obj = $ref->newInstance();
        self::$_obj[$key] = $obj;
        return $obj;
    }
}