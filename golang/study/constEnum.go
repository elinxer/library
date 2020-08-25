package main

import (
	"fmt"
)

// 定义命名为普通变量即可，go大写有其他使用含义
func consts() {

	// a，b常量没有定义类型，故使用的时候会根据需要的类型自动转换
	// 也可以直接定义固定的类型
	const a, b = 1, 111

	const (
		c        = 333
		filename = "file.txt"
	)

}

// 枚举类型，可以根据一个指定的公式往下赋值定义的常量
func enums() {

	// 公式自增
	const (
		cpp = iota
		_
		python
		golang
	)

	// 公式计算KB,MB,GB等
	const (
		b = 1 << (10 * iota)
		kb
		mb
		gb
		tb
		pb
	)

	fmt.Println(cpp, python, golang)
	fmt.Println(b, kb, mb, gb, tb, pb)
}

func main() {
	consts()
	enums()
}
