package mock

import "fmt"

type Retriever struct {
	Contents string
}

func (r Retriever) Get(url string) string {
	return r.Contents
}

// 实现者
func (r *Retriever) Post(url string, form map[string]string) string {
	r.Contents = form["contents"]
	return "ok"
}

func (r *Retriever) String() string {
	return fmt.Sprintf("Retriver: {Contents=%s}", r.Contents)
}
