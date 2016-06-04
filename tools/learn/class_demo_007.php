<?php
// PHP魔术方法 __toString()

/*
__toString() 的作用
当我们调试程序时，需要知道是否得出正确的数据。比如打印一个对象时，看看这个对象都有哪些属性，
其值是什么，如果类定义了toString方法，就能在测试时，
echo打印对象体，对象就会自动调用它所属类定义的toString方法，格式化输出这个对象所包含的数据。

*/
class Person{
    private $name = "";
    function __construct($name = ""){
                 
        $this->name = $name;

    }

    function say(){
                 
        echo "Hello,".$this->name."!<br/>";  

    }

    function __tostring(){//在类中定义一个__toString方法
        // __tostring()方法体中需要有一个返回值。
        return  "Hello,".$this->name."!<br/>";    
    }
}
$WBlog = new Person('E林夕');

echo $WBlog;//直接输出对象引用则自动调用了对象中的__toString()方法

// $WBlog->say();//试比较一下和上面的自动调用有什么不同


?>