<?php
/**
 * PHP抽象类与接口
 */

/**
 * 含有抽象方法的类必须定义为抽象类
 * 抽象类可以不定义抽象方法
 * 不允许直接实例化，需要通过子类继承
 * 子类必须把父类所有的抽象方法都重写一次
 */
abstract class Persion {
	// 定义抽象方法：修饰词 abstract 
	// 抽象方法是没有方法体{}的
	// 方法可以传递参数
	public abstract function say();

	public abstract function cut($things);

	public function test() {
		echo "test";
	}

}

// 子类
class Student extends Persion {
	public function say() {
		echo "say";
	}

	public function cut($things) {
		echo "cut ".$things;
	}

}

$student = new Student();

$student->say();
$student->cut("my pen");