<?php
// 工厂模式
abstract class Operation{ 
    abstract public function getValue($num1,$num2); 

    public function getAttr(){ 
        return 1; 
    } 
}

class Add extends Operation{ 
    public function getValue($num1, $num2){ 
        return $num1+$num2; 
    } 
} 
class Sub extends Operation{ 
    public function getValue($num1, $num2){ 
        return $num1-$num2; 
    } 
} 
class Factory{ 
    public static function CreateObj($operation){ 
        switch ($operation){ 
            case '+': return new Add(); 
            case '-': return new Sub(); 
        } 
    } 
} 

$Op=Factory::CreateObj('-'); 

echo $Op->getValue(3, 6); 