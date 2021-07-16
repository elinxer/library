//+build ingore
package main

import "fmt"

func variables() {

	var a int
	var b string

	fmt.Printf("%d %q\n", a, b)
}

func variable2() {
	var a, b, c = 2, 5, "fstring"
	fmt.Println(a, b, c)
}

func variableShort() {
	a, b, c := 10, 32, "string2"
	fmt.Println(a, b, c)
}

// 外层定义包内部变量必须要var定义
var bb = "11111"

var (
	aa = 2222
)

func main() {
	variables()
	variable2()
	variableShort()
	fmt.Println(aa, bb)
}
