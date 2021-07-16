package main

import (
	"fmt"
	"library/golang/study/queue2"
)

func main() {

	q := queue2.Queue2{1}

	q.Push(2)
	q.Push(3)

	fmt.Println(q.Pop())
	fmt.Println(q.Pop())
	fmt.Println(q.IsEmpty())

	q.Push("abc32132")

	fmt.Println(q.Pop())
	fmt.Println(q.Pop())

}
