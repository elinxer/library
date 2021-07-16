//+build ignore
package main

import (
	"fmt"
	"io/ioutil"
)

/*

go语言函数可以返回多个值

一般是会返回两个值，一个正常值，一个错误err，也可以返回三个以上

if条件里面可以赋值，然后在去判断bool值

if里面的赋值只在if里面有效，作用域只在if，外部不能使用的


switch 不需要break自动打断，默认就是打断，如果不需要打断，反而要使用关键字fallthrough主动不打断，这是和其他语言不一样的。

panic 函数自动中断程序执行，并抛出错误

switch中可以没有表达式，如下一个方法，当然也可以有

*/

func grade(score int) string {
	g := ""
	switch {
	case score < 60:
		g = "F"
	case score < 80:
		g = "C"
	case score < 90:
		g = "B"
	case score <= 100:
		g = "A"
	default:
		// 中断运行，panic输入参数是字符串，一个参数
		panic(fmt.Sprintf("Err：%s", score))
	}
	return g
}

func main() {

	filename := "abc.txt"

	contents, err := ioutil.ReadFile(filename)

	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Printf("%s\n", contents)
	}

	filename = "abcd.txt"

	if contents2, err2 := ioutil.ReadFile(filename); err2 != nil {
		fmt.Println(err2)
	} else {
		fmt.Println(contents2)
	}

	// 注意换行必须要逗号结尾
	fmt.Println(
		grade(80), grade(101),
	)

}
