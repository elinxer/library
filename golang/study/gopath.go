//+build ignore
package main

import "fmt"

func testSparse() {

	s := intsets.Sparse{}

	s.Insert(1)

	fmt.Println(s.Has(1))
}

func main() {

	testSparse()

}
