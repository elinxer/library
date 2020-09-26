package main

import "fmt"

func adder() func(int) int {
	sum := 0
	return func(v int) int {
		sum += v
		return sum
	}
}

func main() {

	a := adder()

	for i := 0; i < 45; i++ {
		fmt.Printf("i=1+2+3....%d=%d\n", i, a(i))
	}

}
