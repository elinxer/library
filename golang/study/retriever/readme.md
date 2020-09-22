### 接口

1.接口的实现是隐式的

只有实现接口的方法就行，不一定要实现整个接口


2.指针变量自带指针

3.接口变量同样采用值传递，几乎不需要使用接口的指针

4.指针接收者实现只能以指针方式使用，值接收者两者都可以

5.接口interface{}代表任何类型

6.接口定义，实现者，使用者

接口定义后，会有实现者去实现接口的方法属性之类的，然后使用者可以是实际组装后业务需要的方式，灵活多变。

```
//定义
type Retriever interface {
	// 不用加function关键词表名，括号就代表了
	Get(url string) string
}

// 实现者 这里会自动识别到是哪个接口的
func (r Retriever) Get(url string) string {
	return r.Contents
}

// 使用者
// 这里是调用者
func download(r Retriever) string {
	return r.Get("https://www.baidu.com")
}


```