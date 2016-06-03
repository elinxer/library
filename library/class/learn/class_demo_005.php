<?php
// PHP多态应用


//+----------------------------------------------------------

// 声明USB统一运行接口
interface USB {
	// 各种USB设备统一运行的方法
	public function run();

}
//+----------------------------------------------------------
// 鼠标设备
class mouse implements USB {
	// 重写通用运行方法
	public function run() {
		// 运行本设备的方法
		$this->init();
	}

	// 本设备运行的方法
	public function init() {
		echo 'mouse running...';
	}
}
// 存储设备
class store implements USB {
	public function run() {
		$this->init();
	}
	public function init() {
		echo "store running...";
	}
}
// 键盘设备
class key implements USB {
	public function run() {
		$this->init();	
	}
	public function init() {
		echo "key running...";
	}
}

//+----------------------------------------------------------
// 定义一台电脑
class computer {

	// 电脑自动运行插入的USB设备
	public function useUSB($obj) {
		// 获取传入对象，并调用传入对象的run方法
		$obj->run();
	}
}

// 电脑运行各种usb设备
$computer = new computer();
// 插入鼠标
$computer->useUSB(new mouse());
echo "<hr>";
// 插入键盘
$computer->useUSB(new key());
echo "<hr>";
// 插入存储设备
$computer->useUSB(new store());

