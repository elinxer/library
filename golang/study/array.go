//+build ignore
package main

import "fmt"

/*

1.定义数组有几种方式
:= 定义要赋默认值
定义数组默认值都是根据类型决定，int则默认值是0

2.数组是值类型

数组类型并不常用，主要使用的是slice（切片）类型

*/

// arr [5]int 这是数组
// arr []int 这是切片
func printArr(arr [5]int) {
	arr[0] = 100
	for i, v := range arr {
		fmt.Println(i, v)
	}
}

// 使用指针参数，仅仅参数加*也是可以的
func printArr1(arr *[5]int) {
	arr[0] = 100
	for i, v := range arr {
		fmt.Println(i, v)
	}
}

func main() {

	// 定义5个长度的数组
	var arr1 [5]int
	// 使用:= 要赋默认值
	arr2 := [3]int{1, 2, 3}
	// 使用[...] 定义可变长度数组
	arr3 := [...]int{1, 4, 5, 6, 7}
	// 二维数组，定义4行5列
	var grid [4][5]int
	fmt.Println(arr1, arr2, arr3)
	fmt.Println(grid)

	// i是下标，v是值，不使用可以用占位符 _
	// 也可以使用len(arr3) 然后for循环下标一个个显示获取
	for i := 0; i < len(arr3); i++ {
		fmt.Println(arr3[i])
	}
	for i, v := range arr3 {
		fmt.Println(i, v)
	}
	fmt.Println("======")

	fmt.Println("print arr1", arr1)
	printArr(arr1)

	// 并没有改变arr3内容
	printArr(arr3)
	fmt.Println("print arr2", arr3)

	printArr1(&arr3)
	fmt.Println("print arr2", arr3)
	// result print arr2 [100 4 5 6 7] 第一个已经被改变了

}
