@[toc] 

# 前提
开发环境：vscode配套装完

go版本： go version go1.14.4 windows/amd64

# 基础说明

## 1.文件结构

```
package main

import( "fmt")

func main() {
	fmt.Println("fuck", "trump!")
}
```
 
以上是go语法基础结构，也是第一个代码。

注意：**Go常用只能用双引号**，单引号初学阶段可以直接放弃记忆，当他不存在，后续强大了自己查原因。

## 2.语法基础

**定义变量**

```
// 方式1
var test string;
// 方式2
test2 := "will be string type!"
test3, test4 := "test3", "test4"
// 方式3
var test3, test4 string
```
注意：**go中未使用的变量不允许定义**

**数据类型以及定义**


| 序号 |	类型和描述|
|--|--|
|1 |	布尔型 布尔型的值只可以是常量 true 或者 false。一个简单的例子：var b bool = true。
|2 |	数字类型整型 int 和浮点型 float32、float64，Go 语言支持整型和浮点型数字，并且支持复数，其中位的运算采用补码。
|3|	字符串类型，字符串就是一串固定长度的字符连接起来的字符序列。Go 的字符串是由单个字节连接起来的。Go 语言的字符串的字节使用 UTF-8 编码标识 Unicode 文本。|
| 4 |	派生类型:包括：|
||(a) 指针类型（Pointer）|
||(b) 数组类型|
||(c) 结构化类型(struct)|
||(d) Channel 类型|
||(e) 函数类型|
||(f) 切片类型|
||(g) 接口类型（interface）|
||(h) Map 类型      |

```
// 定义数字
var int_1 int
int_1 = 1
fmt.Println(int_1)
int_2 := 2
fmt.Println(int_2)

var int_8 int8
int_8 = 127 // 2^8-1=127
fmt.Println(int_8)

var int_16 int16
int_16 = 111
fmt.Println(int_16)

// 定义字符串
var string_1 string
string_1 = "fuck trump"
string_2 := "fuck trump fmy"
fmt.Println(string_1, string_2)
string_3 := `
mutil line
`

// 定义数组
var balance = [...] float32 {1000.0, 2.0, 3.4, 7.0, 50.0}
fmt.Println(balance)
var balance2=[...]int{123,323,21,2,1,2,3}
fmt.Println(balance2)
balance2[0]= 1000000000000
fmt.Println(balance2)
// 打印数组长度
fmt.Println(len(balance2))

// Channel 通道类型
chan_var := make(chan int)
go func() {
	chan_var <- 111
}()
chan_2 := <- chan_var
fmt.Println(chan_var)
fmt.Println(chan_2)

// 切片类型
var slince_1 []string
fmt.Println(slince_1)
len := 10
var slice1 []string = make([]string, len)
//也可以简写为
slice2 := make([]string, len)
fmt.Println(slice1, slice2)

```

**for循环的两种方式**

```
var balance2=[...]int{123,323,21,2,1,2,3}
fmt.Println(balance2)
balance2[0]= 1000000000000
fmt.Println(balance2)
for i:=0;i<len(balance2);i++ {
    fmt.Println(balance2[i])
}
for _, value := range balance2 {
    fmt.Println(value)
}
```
其中由于go未经使用的变量不允许定义，故使用range时，会返回两个变量，这里定义为key和value，而key我并没有用到，故要使用占位符 `_` ，`_` 代表赋值但不使用，相对赋值给一个占位而已。

值得注意的是range可以应用于数组，切片，map等类型中。

**结构体类型**

结构体类型大致可以弥补类模式缺少的情况，可以组建一个结构体当做类来使用，也具备初始化和赋值方式。

```
package main

import "fmt"

type struct_book struct {
	title string
	author string
	number int
}

func main() {
	var Book struct_book
	Book.title = "BlogTitle"
	fmt.Printf("Book title: %s", Book.title)
}
```

**channel通道类型详解**

通道类型作用于几个协程中都有使用的变量的传递，防止协程中一个变量值和另一个协程使用不一致。

```
// 创建通道变量，使用切片创建+chan关键字
chan_var := make(chan int) 
go func() {
	// 协程中赋值，赋值操作符 <-
	chan_var <- 111
}()
// 由于通道变量是引用传递，即变量存储的是内存地址，故要将通道变量赋值出来给新的变量
chan_2 := <- chan_var
fmt.Println(chan_var)
fmt.Println(chan_2)
```

## 3.切片类型

Go 语言切片是对数组的抽象。Go 数组的长度不可改变，在特定场景中这样的集合就不太适用，Go中提供了一种灵活，功能强悍的内置类型切片("动态数组"),与数组相比切片的长度是不固定的，可以追加元素，在追加时可能使切片的容量增大。

切片类型会常常用到，故这里作详细说明。


## 4.指针类型

指针类型指向一个内存地址，用引用，传递引用等。指针类型不能之前赋值一个实际的值，可以交换内存地址。

```
	var ptr_1 * int
	string_4 := 100;
	ptr_1 = &string_4
	fmt.Println(ptr_1)
```


# 错误处理机制

go并没有异常捕捉机制，错误只能手动返回和处理，且需要使用接口类型：Error 的struct，改接口属于底层，直接使用即可。
Demo 来源 [菜鸟教程](https://www.runoob.com/go/go-error-handling.html)

```
// 定义一个 DivideError 结构
type DivideError struct {
    dividee int
    divider int
}
// 实现 `error` 接口
func (de *DivideError) Error() string {
    strFormat := `
    Cannot proceed, the divider is zero.
    dividee: %d
    divider: 0
`
    return fmt.Sprintf(strFormat, de.dividee)
}
// 定义 `int` 类型除法运算的函数
func Divide(varDividee int, varDivider int) (result int, errorMsg string) {
    if varDivider == 0 {
		dData := DivideError {
				dividee: varDividee,
				divider: varDivider,
		}
		errorMsg = dData.Error()
		return
    } else {
		return varDividee / varDivider, ""
    }
}
func main() {
    if _, errorMsg := Divide(100, 0); errorMsg != "" {
        fmt.Println("errorMsg is: ", errorMsg)
    }
}
```

# 参考文献

1.[菜鸟教程go教程](https://www.runoob.com/go/go-error-handling.html)
2.[Go Error处理](https://www.jianshu.com/p/75d3682cd135)