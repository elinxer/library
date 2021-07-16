//+build ingore
package main

import (
	"fmt"
)

func main() {

	test1 := 1

	fmt.Print(&test1)
	fmt.Println(test1)
	fmt.Println("Test")
}
