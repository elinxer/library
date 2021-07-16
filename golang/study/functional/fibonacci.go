package main

import (
	"bufio"
	"fmt"
	"io"
	"strings"
)

// 斐波那列系数

// a,b两个数
// 1, 1, 2, 3, 5, 8, 13...
//    a, b
// 	     a, b
func fibo() func() int {
	a, b := 0, 1
	return func() int {
		a, b = b, a+b
		return a
	}
}

// 给函数实现接口 =====================

func fibo2() intGen {
	a, b := 0, 1
	return func() int {
		a, b = b, a+b
		return a
	}
}

type intGen func() int

// 斐波那列系数生成器
// 参数p, 返回n,err
func (g intGen) Read(p []byte) (n int, err error) {
	next := g() // 去下一个元素
	// 然后写入p
	// 找一个已经实现了reader的来代理实现底层
	s := fmt.Sprintf("%d\n", next)
	// 下一个中断
	if next > 1000 {
		// 结束
		return 0, io.EOF
	}
	// TODO: incorrect if p is too small
	return strings.NewReader(s).Read(p)
}

func printContent(reader io.Reader) {
	scanner := bufio.NewScanner(reader)
	for scanner.Scan() {
		fmt.Println(scanner.Text())
	}
}

func main() {

	f := fibo()
	fmt.Println(f())
	fmt.Println(f())
	fmt.Println(f())
	fmt.Println(f())
	fmt.Println(f())
	fmt.Println(f())
	fmt.Println(f())
	fmt.Println(f())
	fmt.Println()

	f2 := fibo2()
	printContent(f2)

}
