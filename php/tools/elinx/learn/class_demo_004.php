<?php

// PHP 内部接口技术

// 抽象方法 关键字interface
// 接口中可以声明常量也可以声明抽象方法
// 接口不能直接实例化，需要一个类实现，并重写所有抽象方法 使用implements
// 一个类可以实现多个接口，解决了PHP单继承的问题
interface Persion {
	const NAME = "TEST";
	// 定义抽象方法
	public function say();

	public function cut();

	// public function eat() { echo 1; } //这是错误的定义方式
}

// 抽象类
interface Learn {
	public function study();
}


class Student implements Persion,Learn {
	public function say() {
		echo 1;
	}

	public function cut() {
		echo 3;
	}

	public function study() {
		echo "study";
	}
}

$student = new Student();

$student->say();
echo "<br>";
echo student::NAME; //访问常量
echo "<br>";
$student->study();