<?php
/**
 * +-----------------------------------------------
 * 实现认证装饰器
 * +-----------------------------------------------
 */


namespace Common\CustomApi\Decoration;


use Common\CustomApi\Base\BaseDecorator;

class Decorator_RealnameAuth extends BaseDecorator
{
    public function handle(array $data): array
    {
        

        return $data;
    }

}