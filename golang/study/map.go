//+build ingore
package main

import "fmt"

/*

创建  make(map[string]int)

获取 m[key]

key不存在获取map的初始值，比如string类型，初始值就是空字符串

m[key] 会返回两个值，一个是值，一个是存在状态

可以用来判断是否存在
value, ok := m[key]

// 遍历key 或value 且是无序的，可以转到slice，排序后再遍历

元素个数使用：len(m)

map的key什么类型可以作为key？

map使用的哈希表，必须可以比较key是否相等

除了slice，map，function的内建类型都可以作为key

struct类型不包含上述字段，可以作为key

示例：寻找最长不含有重复字符的子串


*/

func main() {
	// 定义一个map
	m := map[string]string{
		"name": "Elinx",
		"job":  "PHP",
		"sex":  "boy",
	}
	fmt.Println(m)

	// 定一个空map
	m2 := make(map[string]int) // m2 == empty map
	fmt.Println(m2)

	// 定义一个m3变量
	var m3 = make(map[string]int) // m3 == nil
	fmt.Println(m3)

	// 变量

	// 遍历key 或value 且是无序的
	for k, v := range m {
		// 注意每次打印的顺序是不一样的
		// map是hash map是无序的
		fmt.Println(k, v)
	}

	fmt.Println("Getting Map value：")
	job := m["job"]
	fmt.Println(job)
	// jobb并不存在，会返回一个空串
	jobb := m["jobb"]
	fmt.Println(jobb)

	// 判断一个键知否存在
	if jobb, ok1 := m["jobb"]; ok1 {
		fmt.Println(jobb)
	} else {
		fmt.Println("jobb not found!")
	}

	fmt.Println("Deleting Map value：")
	name, ok := m["name"]
	fmt.Println(name, ok)
	// 删除一个键
	delete(m, "name")

	name, ok = m["name"]
	fmt.Println(name, ok)

	// 示例：寻找最长不含有重复字符的子串 来源LeetCode
	// abcabcbb -> abc
	// bbbbb -> b
	// pwwkew -> wke

	


}
