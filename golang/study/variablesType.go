//+build ignore
package main

import (
	"fmt"
	"math"
	"math/cmplx"
)

/*
	变量定义要点回顾
	1.变量类型写在变量名后面
	2.编译器可以推测变量类型
	3.没有char，只有rune，rune还是32位的
	4.原生支持复数类型

	go语言类型数字代表的是位数，如int32是int32位


	复数i 欧拉公式，i是符号
	根号负一
	i = √-1


*/

func euler1() {
	// 直接数字和i字母结合，编译器会自动识别为复数类型
	// 3 是实部 4是虚部，i是复数符号
	c := 3 + 4i
	// 对c取模（cmplx库函数叫绝对值）
	fmt.Println(cmplx.Abs(c))
}

func euler2() {
	// 底数是E
	str := cmplx.Pow(math.E, 1i*math.Pi) + 1
	fmt.Println(str)
	// 采用e为底数可以直接使用欧拉公式
	str2 := cmplx.Exp(1i*math.Pi) + 1
	fmt.Println(str2)
	fmt.Printf("%.3f \n", str2)
}

// 强制类型转换
func triangle() {
	var a, b int = 3, 4
	var c int

	// 计算结果为float，采用int()方式强行转int类型才能赋值给c
	c = int(math.Sqrt(float64(a*a + b*b)))

	fmt.Println(c)
}

func main() {

	/*

		Go是内建变量类型

		bool string

		有符号整数
		int, int8, int16, int32, int64，uintptr 指针
		无符号整数
		uint, uint8, uint16, uint32, uint64

		如果不规定长度如int则跟随操作系统决定长度
		如果一定要长的可以直接定义int64即可

		byte 8位, rune字符型4字节32位

		float32, float64

		复数类型
		complex64 实部和虚部分别是32位, complex128 实部和虚部分别是64位

	*/

	// 欧拉公式
	euler1()
	euler2()

	// 类型转换
	triangle()

}
