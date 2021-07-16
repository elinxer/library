//+build ingore
package main	


import (
"fmt"
)



func main() {


	var int_1 int

	int_1 = 1
	fmt.Println(int_1)

	int_2 := 2

	fmt.Println(int_2)

	var string_1 string
	string_1 = "11111111"
	string_2 := "2222222"

	fmt.Println(string_1, string_2)

	// ============================


	var int_8 int8

	int_8 = 127 // 2^8-1=127
	fmt.Println(int_8)

	var int_16 int16

	int_16 = 111
	fmt.Println(int_16)


	var balance = [...] float32 {1000.0, 2.0, 3.4, 7.0, 50.0}

	fmt.Println(balance)

	var balance2=[...]int{123,323,21,2,1,2,3}
	fmt.Println(balance2)

	balance2[0]= 1000000000000
	fmt.Println(balance2)


	for i:=0;i<len(balance2);i++ {
	    fmt.Println(balance2[i])
	}

	for _, value := range balance2 {
	    fmt.Println(value)
	}

	j := 2222
	i := 333
	sum := demo_func(j,i)
	fmt.Println(sum)

}

func demo_func(j, i int) int {
  sum := j+i
  return sum
}


















