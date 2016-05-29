<?php
/**
 * PHP系统自带异常处理
 */


try {
	$num = 2;
	if ($num ==1) {
		echo 'success';
	} else {
		// 参数第一个为异常信息，第二个为异常码
		throw new Exception("Error Processing Request", 111);
		
	}


} catch(Exception $e) {
	// 输出错误文件地址
	echo $e->getFile();
	echo "<br>";
	// 输出错误行
	echo $e->getLine();
	echo "<br>";
	// 输出错误码
	echo $e->getCode();
	// 异常信息
	echo $e->getMessage();
}

/*
// PHP系统异常类
class Exception {
	protected $message ="Unknown exception ";//异常信息
	protected $code = 0; //异常码
	protected $file; //错误文件
	protected $line; //错误行

	function __construct($message=null,$code=0) {
		final function getMessage(); //错误信息获取
		final function getCode(); //错误识别码
 		final function getFile(); //错误文件
		final function getLine(); //错误行
		final function getTrace(); //backTrace返回数组
		final function getTraceAsString(); //已经转化成字符串的backTrace信息
		function __toString(); //可输出的字符串
	}

}
*/