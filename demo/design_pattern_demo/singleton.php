<?php
//单例模式
class Database{
    protected static $db;
    //构造方法私有，防止在外层直接new
    private function __construct(){
        //code
    }
 
    static function getInstance(){
        if (self::$db) {
            return self::$db;
        }else{
            self::$db = new self();
            return self::$db;
        }
    }

    // 禁止克隆
    public function __clone() 
    { 
        trigger_error("Only one connection"); 
    } 
}
 
$db  = Database::getInstance();
$db1 = Database::getInstance();
$db2 = Database::getInstance();

var_dump($db, $db1, $db2);
#object(Database)#1 (0) {
#}
#object(Database)#1 (0) {
#}
#object(Database)#1 (0) {
#}