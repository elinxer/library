//+build ignore
package main

import "fmt"

/*

slice （切片）

go文档描述：slice是数组的视图

// 这是数组
arr := [...]int{0, 1, 2, 3, 4, 5, 6, 7}

//这是slice
s := arr[1:]

*/

// 这样定义参数就是切片参数
// 并且在内容修改s参数会影响外部，这是类似引用传递的，slice是指针指向数组的
func updateSlice(s []int) {
	s[0] = 100
}

func main() {

	arr := [...]int{0, 1, 2, 3, 4, 5, 6, 7}

	// 下标从0开始，按个数获取

	fmt.Println(arr[2:6])
	fmt.Println(arr[:6])
	fmt.Println(arr[2:])
	fmt.Println(arr[:])

	s1 := arr[2:]
	s2 := arr[:]

	fmt.Println("s1:", s1)
	fmt.Println("s2:", s2)

	updateSlice(s1)
	fmt.Println("After update s1:", s1)
	fmt.Println("arr ori:", arr)

	updateSlice(s2)
	fmt.Println("After update s2:", s2)
	fmt.Println("arr ori:", arr)

	// 上面进行的slice修改都会改变arr的值

	// 可以对slice取slice
	fmt.Println("Reslice:")
	fmt.Println(s2)
	s2 = s2[:5]
	fmt.Println(s2)
	s2 = s2[2:]
	fmt.Println(s2)

	fmt.Println("================================")

	// cap(s) 底层容器大小
	fmt.Println("arr:", arr)
	s1 = arr[2:6]
	fmt.Println("s1:", s1)
	s2 = s1[3:5]
	fmt.Println("s2:", s2)
	// arr: [100 1 100 3 4 5 6 7]
	// s1: [100 3 4 5]
	// s2: [5 6]
	// s2的6在s1里面不存在，但结果取到了，因为容器大，会拓展往上取
	// 如果s2 = s1[3:7] 超过底层容器的大小，往后没有了，则会报错
	// slice可以向后拓展，查找隐藏的，但不可以向前拓展
	// s[i] 不可以超越len(arr)，后向后拓展不可超过底层数组caps(arr)

	fmt.Printf("s1=%v, len=%d, caps(s1)=%d  \n", s1, len(s1), cap(s1))
	fmt.Printf("s2=%v, len=%d, caps(s2)=%d \n", s2, len(s2), cap(s2))
	

	// 10直接加入到了arr里面，但s4,s5并没有加入arr
	// 
	s3 := append(s2, 10)
	s4 := append(s3, 11)
	s5 := append(s4, 12)

	fmt.Println(s3, s3, s5)
	// s4和s5不再是对arr的view，而且go给与的一个新的数组用于填充
	fmt.Println(arr)

	// 添加元素时如果超越arr的cap，系统会重新分配更大的一个新的底层数组用于操作
	// 由于值传递的关闭，必须接受append的返回值
	
}
