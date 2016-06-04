<?php
// PHP单例模式

/**
单例模式属于创建型模式，何为创建型模式，即创建型模式抽象了实例化过程。
他们帮助一个系统独立于如何创建、组合和表示他的那些对象。一个类创建型模式使用继承改变被实例化的类。
而一个对象创建型模式将实例化委托给另一个对象

概述：保证一个类仅有一个实例，并提供一个访问它的全局访问点

使用前提：

1 构造函数需要标记为private（访问控制：防止外部代码使用new操作符创建对象），
单例类不能在其他类中实例化，只能被其自身实例化。

2 拥有一个保存类的实例的静态成员变量

3 拥有一个访问这个实例的公共的静态方法（常用getInstance()方法进行实例化单例类，
通过instanceof操作符可以检测到类是否已经被实例化）

4 需要创建__clone()方法防止对象被复制（克隆）

为什么要使用单例模式？

1 php的应用主要在于数据库应用，所以一个应用中会存在大量的数据库操作，使用单例模式，
则可以避免大量的new操作消耗的资源

2 如果系统中需要有一个类来全局控制某些配置信息, 那么使用单例模式可以很方便的实现. Config

3 在一次页面请求中, 便于进行调试, 因为所有的代码(例如数据库操作类db)都集中在一个类中, 
我们可以在类中设置钩子, 输出日志，从而避免到处var_dump, echo。

*/

try{
	$singleObj = \haibao\design\web\common\design\single\Single::getInstance();
	//$singleObj1 = clone $singleObj;
	echo $singleObj->getData();
}catch (\Exception $e){
	echo $e->getMessage();exit;
}
//-----
namespace haibao\design\web\common\design\single;
class Single{
	public static $_instance;
	private function __construct(){}

	public function __clone(){
		trigger_error('Clone is not allow!', E_USER_ERROR);
	}
	public static function getInstance(){
		if(!(self::$_instance instanceof self)){
		self::$_instance = new self;
	}
		return self::$_instance;
	}
	public function getData(){
		return "<br/>".rand(1,100)."<br/>";
	}
}

