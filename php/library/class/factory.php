<?php
 /** 
 * 简单工厂模式 
 * ------------- 
 * @author      zhaoxuejie <zxj198468@gmail.com> 
 * @package     design pattern  
 * @version     v1.0 2011-12-14 
 */  
  
interface  Comput {  
    public function getResults();   
}  
  
//操作类  
class Operation {  
    protected  $Number_A = 0;  
    protected  $Number_B = 0;  
    protected  $Result = 0;  
      
    //赋值  
    function setNumber($Number_A, $Number_B){  
        $this->Number_A = $Number_A;  
        $this->Number_B = $Number_B;  
    }  
      
    //清零  
    function clearResult(){  
        $this->Result = 0;  
    }  
}  
  
//加法  
class OperationAdd extends Operation implements Comput {  
    function getResults(){  
        return $this->Result = ($this->Number_A + $this->Number_B);      
    }  
}  
  
//减法  
class OperationSub extends Operation implements Comput {  
    function getResults(){  
        return $this->Result = ($this->Number_A - $this->Number_B);      
    }  
}  
  
//乘法  
class OperationMul extends Operation implements Comput {  
    function getResults(){  
        return $this->Result = ($this->Number_A * $this->Number_B);      
    }  
}  
  
//除法  
class OperationDiv extends Operation implements Comput {  
    function getResults(){  
        if(intval($this->Number_B) == 0){  
            return $this->Result = 'Error: Division by zero';  
        }  
        return $this->Result = ($this->Number_A / $this->Number_B);      
    }  
}  
  
//工厂  
class OperationFactory {  
    private static $obj;  
      
    public static function CreateOperation($type){  
        try {  
           $error = "Please input the '+', '-', '*', '/' symbols of Math.";  
           switch($type){  
                case '+' :  
                    self::$obj = new OperationAdd();  
                    break;  
                case '-' :  
                    self::$obj = new OperationSub();  
                    break;  
                case '*' :  
                    self::$obj = new OperationMul();  
                    break;  
                case '/' :  
                    self::$obj = new OperationDiv();  
                    break;  
                default:  
                    throw new Exception($error);  
            }  
            return self::$obj;  
          
        } catch (Exception $e) {  
            echo 'Caught exception: ',  $e->getMessage(), "\n";  
            exit;  
        }  
    }  
}  
  
//工厂创建实例  
$obj = OperationFactory::CreateOperation('*');  
$obj->setNumber(3, 4);

echo $obj->getResults();