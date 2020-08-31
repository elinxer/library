//+build ignore
package main

import "fmt"

/*
参数传递

1.值传递
2.引用传递

c,c++可以值传递可以引用传递

java，Python都是是引用传递，除了系统内自建类型有值传递

变量 a 传递到函数内过程被改变，外部访问a变更了为引用传递，没变为值传递

go只有值传递一种方式

go变量传递到函数里面，函数内部是复制一份变量在使用



go语言指针不能运算


*/

func swap(a, b int) {
	b, a = a, b
}

// 定义指针类型的参数
// swapPtr(&a, &b)
func swapPtr(a, b *int) {
	*b, *a = *a, *b
}

func swapReturn(a, b int) (int, int) {
	b, a = a, b
	return a, b
}

func main() {

	// 因为go是值传递这样是无法交换值的，a,b还是原值
	a, b := 3, 5
	swap(a, b)
	fmt.Println(a, b)

	// 指针类型要传递变量地址，要加&
	swapPtr(&a, &b)
	fmt.Println(a, b)

	// 常用方式=使用函数返回值交换
	a, b = swapReturn(a, b)
	fmt.Println(a, b)
}
