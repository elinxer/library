package main

import "fmt"

func adder() func(int) int {
	// 函数内变量，也叫自由变量
	sum := 0
	return func(v int) int {
		// 闭包函数内的变量处理逻辑，可以使用外层的sum，他们在同一个作用域下，这点是特殊的
		// 自由变量的垃圾回收会直接往下索引找到所有的使用者，一个个检查是否再用然后才标记销毁
		sum += v
		return sum
	}
}

// 正统函数式 =========
// 返回的是当前的值，以及下一轮要加的逻辑，这是递归的逻辑
// 因为正统的方式里面没有状态，这样返回的数据状态要存入下一个函数里面作为中转
type iAdder func(int) (int, iAdder)
// 没有用到变量，都是函数和传入的参数
func adder2(base int) iAdder {
	return func(v int) (int, iAdder) {
		return base + v, adder2(base + v)
	}
}

// =================

func main() {

	a := adder()

	for i := 0; i < 10; i++ {
		fmt.Printf("i=1+2+3....%d=%d\n", i, a(i))
	}

	aa := adder2(0)

	for i := 0; i < 10; i++ {
		var s int
		s, aa = aa(i)
		fmt.Printf("0+1+2+3+...+ %d=%d\n", i, s)
	}

}
