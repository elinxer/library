//+build ignore
package main

import (
	"fmt"
	"math"
	"reflect"
	"runtime"
)

/*

1.函数可以返回多个值如div函数

2.函数返回值可以命名，命名可以使用或调用者修改新命名

3.函数可以作为参数传递，可以传递匿名函数作为参数

4.go只有可变参数列表，没有默认参数和可选参数

*/
// sum(1, 2, 3, 4, 5) 参数可以作为数组列表传入
func sum(numbers ...int) int {
	s := 0
	for i:= range numbers {
		s += numbers[i]
	}
	return s
}

func eval(a, b int, op string) (int, error) {

	switch op {
	case "+":
		return a + b, nil
	case "-":
		return a - b, nil
	case "*":
		return a * b, nil
	case "/":
		//return a / b
		q, _ := div(a, b)
		return q, nil
	default:
		//panic("Err Operation:" + op) // 中断运行
		return 0, fmt.Errorf("Error op: %s", op)
	}

}

func div(a, b int) (q, r int) {
	// q = a / b
	// r = a % b
	// return
	return a / b, a % b
}

// 函数式编程
func apply(op func(int, int) int, a, b int) int {
	// 获取函数名称的方式
	p := reflect.ValueOf(op).Pointer()
	opName := runtime.FuncForPC(p).Name()
	fmt.Println("Calling function %s with args:"+"(%d, %d)", opName, a, b)
	return op(a, b)
}

func pow(a, b int) int {
	return int(math.Pow(float64(a), float64(b)))
}

func main() {

	q, _ := div(13, 4)
	fmt.Println(q)

	fmt.Println(eval(15, 3, "/"))

	fmt.Println(eval(3, 4, "x"))

	// 错误处理
	if result, err := eval(3, 4, "x"); err != nil {
		fmt.Println("Error:", err)
	} else {
		fmt.Println(result)
	}

	// 函数式编程
	// 传递一个函数参数进行处理数据
	result := apply(
		func(a, b int) int {
			return a * b
			//return int(math.Pow(float64(a), float64(b)))
		}, 3, 4)
	// Calling function %s with args:(%d, %d) main.main.func1 3 4

	fmt.Println(result)

	// 可变参数列表


	fmt.Println(sum(1, 2, 3, 4, 5))

}
