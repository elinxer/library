// duck typing
// 像鸭子走路，像鸭子叫，像鸭子，就是鸭子
// 这种就是接口的认定方式，使用者来认定
package main

import (
	"fmt"
	"library/golang/study/retriever/mock"
	"library/golang/study/retriever/real"
	"time"
)

type Retriever interface {
	// 不用加function关键词表名，括号就代表了
	Get(url string) string
}

// 这里是调用者
func download(r Retriever) string {
	return r.Get("https://www.baidu.com")
}

func inspect(r Retriever)  {
	fmt.Printf("%T %v:\n", r, r)
	fmt.Println("Type switch:")
	switch v:= r.(type) {
	case mock.Retriever:
		fmt.Println("Contents:", v.Contents)
	case *real.Retriever:
		fmt.Println("UserAgent:", v.UserAgent)
	}
}

func main() {

	// 当前页面定义的接口
	// 存在一个Get方法需要被实现
	var r Retriever

	//mock包内部的定义的接口实现
	r = mock.Retriever{"this is fake!"}
	//fmt.Println(download(r))

	fmt.Printf("%T %v \n", r, r)

	// real包内部定义的接口实现
	// 如果值指针接受者，则不能再如此创建默认数据
	// 需要使用&作为引用
	//r = real.Retriever{}
	r = &real.Retriever{}
	//fmt.Println(download(r))
	fmt.Printf("%T %v \n", r, r)

	// mock.Retriever {this is fake!} 值
	// real.Retriever { 0s} 这是real内的属性的默认值
	// go语言所有类型都是值类型

	// 这是传值的方式
	// r = real.Retriever{
	// 	UserAgent: "Mozilla/5.0",
	// 	TimeOut:   time.Minute,
	// }

	r = &real.Retriever{
		UserAgent: "Mozilla/5.0",
		TimeOut:   time.Minute,
	}

	// real.Retriever {Mozilla/5.0 1m0s}
	fmt.Printf("%T %v :\n", r, r)

	fmt.Println()

	inspect(r)

	// Type assertion
	realRetriver := r.(*real.Retriever)
	fmt.Println(realRetriver.TimeOut)

	if mockRetriver, ok := r.(mock.Retriever); ok {
		fmt.Println(mockRetriver.Contents)
	} else {
		fmt.Println("Not a mock retriver.")
	}

}
