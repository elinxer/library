package main

import (
	"fmt"
	"os"
)

func tryDefer() {

	// defer 里面有个栈，遵循先进后出
	// 先执行最后的，往前追寻
	// 使用defer，中间有return，panic等直接中断的，也能继续执行！

	defer fmt.Println(11) // 最后打印
	defer fmt.Println(22) // 再打印
	fmt.Println(33)       // 先打印
	panic("master error!")
	fmt.Println(44) // 先打印
}

func writeFile(filename string) {
	file, err := os.Create(filename)
	if	err != nil {
		panic(err)
	}
}

func main() {

	tryDefer()

}
