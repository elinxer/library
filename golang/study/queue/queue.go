package queue

// 新增简单的使用slice+指针的队列封装包

type Queue []int

func (q *Queue) Push(v int) {
	// append会改变q的值，所以传递要用指针
	*q = append(*q, v)
}

func (q *Queue) Pop() int {
	head := (*q)[0]
	*q = (*q)[1:]
	return head
}

func (q *Queue) IsEmpty() bool {
	return len(*q) == 0
}
