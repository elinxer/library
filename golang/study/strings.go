//+build ignore
package main

import (
	"fmt"
	"unicode/utf8"
)

/*

go中的rune相当于char类型

字符串处理基本都在strings包里面或者utf8包

*/

func main() {

	str := "Yes生活也是一种享受！"

	fmt.Println(len(str))
	// 打印16进制
	fmt.Printf("%X \n", str)

	// 每个中文占三个字符， UTF-8编码
	for _, b := range []byte(str) {
		fmt.Printf("%X ", b)
	}
	fmt.Println()

	// 直接打印字符转为Unicode编码
	// 转为4字节的表示返回Unicode编码
	for i, ch := range str {
		fmt.Printf("( %d %X )", i, ch)
	}
	fmt.Println()

	// 查有多少个字符（不是长度）
	fmt.Println("rune count:", utf8.RuneCountInString(str))

	// 一个个拿字符
	bytes := []byte(str)
	for len(bytes) > 0 {
		ch, size := utf8.DecodeRune(bytes)
		// 拿到并重置bytes，可以循环取
		bytes = bytes[size:]
		fmt.Printf("%c ,", ch)
	}
	fmt.Println()

	// 转为rune类型，就可以按实际的文字获取指定的那个字
	// 底层原理还是走的上面流程，这里是快捷方式而已
	// rune类型理解为文本的一个个字符，不管是哪国语言的，存储字节是多少
	for i, ch := range []rune(str) {
		fmt.Printf("(%d %c) ,", i, ch)
	}
	fmt.Println()


	//	Fields, Split, Join
	// Contains, Index
	// ToLower, ToUpper
	// Trim, TrimRight, TrimLeft


}
