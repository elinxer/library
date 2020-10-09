package main

import "fmt"

func adder() func(int) int {
	sum := 0
	return func(v int) int {
		sum += v
		return sum
	}
}

type iAdder func(int) (int, iAdder)

func adder2(base int) iAdder {
	return func(v int) (int, iAdder) {
		return base + v, adder2(base + v)
	}
}

func main() {

	a := adder()
	for i := 0; i < 5; i++ {
		fmt.Printf("i=1+2+3....%d=%d\n", i, a(i))
	}

	fmt.Println()

	b := adder2(0)

	for i := 0; i < 10; i++ {
		var s int
		s, b = b(i)
		fmt.Printf("0+1+...+ %d=%d\n", i, s)
	}

}
