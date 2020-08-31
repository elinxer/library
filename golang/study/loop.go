//+build ignore
package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

// 整数转二进制
// 思路是取模后拼接结果为字符串直接为二进制
func convertToBin(n int) string {
	result := ""
	// 没有起始条件
	for ; n > 0; n /= 2 {
		lsb := n % 2
		// 转为字符串才可以拼接
		result = strconv.Itoa(lsb) + result
	}

	return result
}

func printFile(filename string) {

	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}

	scanner := bufio.NewScanner(file)

	// 没有起始条件没有递增条件，只有结束条件，所以可直接这样写
	for scanner.Scan() {
		fmt.Println(scanner.Text())
	}

}

// 死循环
func forever()  {
	for {
		fmt.Println("a123")
	}
}

// go语言没有while，所有循环都是for处理

func main() {

	//换行必须加逗号，不加的话括号要在同一结束行
	fmt.Println(
		convertToBin(5),
		convertToBin(13),
		convertToBin(5111111),
	)

	//forever()

	printFile("abc.txt")

}
