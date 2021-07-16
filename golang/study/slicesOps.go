//+build ignore
package main

import "fmt"

func printSlice(s []int) {
	fmt.Printf("s=%v, len=%d, cap=%d\n", s, len(s), cap(s))
}

func main() {

	///////////// Creating Slice
	// 定义切片
	var s []int // 变量一定义会是 Zerovalue for slice is nil

	for i := 0; i < 100; i++ {
		// 第一个是0没有值，go并不会报错
		printSlice(s)
		s = append(s, 2*i+1)
	}
	fmt.Println(s)

	s1 := []int{2, 4, 6, 8}
	printSlice(s1)

	// make创建切片，长度是16
	s2 := make([]int, 16)
	// make创建切片，长度是10，cap是32
	s3 := make([]int, 10, 32)
	printSlice(s2)
	printSlice(s3)

	////////////////// Copy Slice

	copy(s2, s1)
	fmt.Println("copy s1 to s2: ", s2)

	////////////////// Delete elements from Slice

	// 没有内建函数删除
	
	// 例如删除8，下标是3，删除下标3
	// [2 4 6 8 0 0 0 0 0 0 0 0 0 0 0 0]
	// 前一段 s2[:3] 拼接 s2[4:] 这样删除下标为3的
	// 但slice是没有直接加的操作的，所以采用append
	// append第一个为整slice，第二个参数为单个加的元素
	// 如果直接s2[4:] 是slice不是单个元素不能直接追加，可以在后面加...代表不断追加直到拿完
	s2 = append(s2[:3], s2[4:]...)
	printSlice(s2)

	////////////////// Poping elements from front
	front := s2[0] // 拿第一个 2
	s2 = s2[1:] // 删除第一个

	fmt.Println(front)
	printSlice(s2)

	////////////////// Poping elements from back

	tail := s2[len(s2)-1] // 拿最后一个 0 
	s2 = s2[:len(s2)-1] // 去掉最后一个

	fmt.Println(tail)
	printSlice(s2)

}
