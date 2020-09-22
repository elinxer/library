package queue2

// 新增简单的使用slice+指针的队列封装包

type Queue2 []interface{}

func (q *Queue2) Push(v interface{}) {
	// append会改变q的值，所以传递要用指针
	// 如果这里要限制传入类型，可以直接限制v的类型，这样传递非指定类型
	// 会报运行时错误
	// v.(int)
	*q = append(*q, v)
}

func (q *Queue2) Pop() interface{} {
	head := (*q)[0]
	*q = (*q)[1:]
	return head
}

func (q *Queue2) IsEmpty() bool {
	return len(*q) == 0
}
