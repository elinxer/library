<?php
// PHP魔术方法 __get() 和 __set()

class Person 
{ 
	//下面是人的成员属性，都是封装的私有成员 
	private $name; //人的名子 
	private $sex; //人的性别 
	private $age; //人的年龄 
	//__get()方法用来获取私有属性 
	private function __get($property_name) 
	{ 
		echo "在直接获取私有属性值的时候，自动调用了这个__get()方法<br>"; 
		if(isset($this->$property_name)) 
		{ 
			return($this->$property_name); 
		} 
		else 
		{ 
			return(NULL); 
		} 
	} 
	//__set()方法用来设置私有属性 
	private function __set($property_name, $value) 
	{ 
		echo "在直接设置私有属性值的时候，自动调用了这个__set()方法为私有属性赋值<br>"; 
		$this->$property_name = $value; 
	} 
} 
$p1=newPerson(); 
//直接为私有属性赋值的操作，会自动调用__set()方法进行赋值 
$p1->name="张三"; 
$p1->sex="男"; 
$p1->age=20; 
//直接获取私有属性的值，会自动调用__get()方法，返回成员属性的值 
echo "姓名：".$p1->name."<br>"; 
echo "性别：".$p1->sex."<br>"; 
echo "年龄：".$p1->age."<br>";




?> 