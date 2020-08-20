// +build ignore
package main

import (
	"encoding/json"
	"fmt"
)

type Person struct {
	Name string
	Age  int
}

type Family struct {
	Persons []Person
}

func main() {
	// 根据结构体生成json
	manJson := Person{
		Name: "Elinx",
		Age:  26,
	}

	man, _ := json.Marshal(manJson)
	fmt.Println(string(man))

	// 解析json到结构体
	jsonStr := []byte(`{"Name":"Elinx","Age":26}`)
	var man2 Person
	json.Unmarshal(jsonStr, &man2)
	fmt.Println(man2)

	// 解析json数组到切片（数组）
	jsonArrStr := []byte(`[{"Name":"Elinx","Age":26}, {"Name":"Twinkle","Age":21}]`)
	var jsonSlice []map[string]interface{}

	json.Unmarshal(jsonArrStr, &jsonSlice)
	fmt.Println(jsonSlice)

	// 解析多维数组
	var f Family

	// 模拟传输的Json数据
	familyJSON := `{"Persons": [{"Name":"Elinx","Age":26}, {"Name":"Twinkle","Age":21}] }`

	fmt.Println("======================")

	// 解析字符串为Json
	json.Unmarshal([]byte(familyJSON), &f)
	// 直接输出并不会展示全部层级，原因待考究
	fmt.Println(f)
	// 从新生成json字符串查看真实结构
	jsons, _ := json.Marshal(f)

	fmt.Println(string(jsons))

	// 直接解析json单key结构，不使用结构体
	uploadJSON := `{"xxxx": "test","zzzz": "1111111"}`
	var jsonSlice2 map[string]interface{}
	json.Unmarshal([]byte(uploadJSON), &jsonSlice2)
	fmt.Println(jsonSlice2)
}
