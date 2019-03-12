<?php
/**
 * PHP面向对象程序设计之魔术方法
 * 克隆魔术方法 __clone()
 */


/**
* 克隆魔术方法
*/
class demo
{
	public $name;
	public $age;
	
	function __construct($name, $age)
	{
		$this->name = $name;
		$this->age = $age;
	}

	public function say() {
		echo "say".$this->name;
	}

	// __clone() 魔术方法，在克隆对象时被自动调用的
	//该方法为自定义克隆操作，默认会有一个公共的克隆方法
	//当前作用：可以对新对象的成员属性进行赋值的
	public function __clone() {
		$this->name = "李四";
		$this->age = 100;
	}
}

$demo = new demo("张三", 20);

$demo->say();

$demo1 = clone $demo;

echo "<hr>";

$demo1->say();

var_dump($demo1);