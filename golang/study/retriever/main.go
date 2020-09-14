// duck typing
// 像鸭子走路，像鸭子叫，像鸭子，就是鸭子
// 这种就是接口的认定方式，使用者来认定
package main

import (
	"fmt"
	"library/golang/study/retriever/mock"
	"library/golang/study/retriever/real"
)

type Retriever interface {
	// 不用加function关键词表名，括号就代表了
	Get(url string) string
}

func download(r Retriever) string {
	return r.Get("https://www.baidu.com")
}

func main() {

	var r Retriever

	r = mock.Retriever{"this is fake!"}

	fmt.Println(download(r))

	r = real.Retriever{}

	fmt.Println(download(r))

}
