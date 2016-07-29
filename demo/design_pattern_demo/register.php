<?php

//注册器模式
class Register
{
    protected static $objects;
 
    static function set($alias, $object){
        self::$objects[$alias] = $object;
    }
    static function get($name){
        return self::$objects[$name];
    }
    function _unset($alias){
        unset(self::$objects[$alias]);
    }
}
$db = 1;
Register::set('db1', $db);
echo Register::get('db1');