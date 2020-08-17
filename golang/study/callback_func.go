package main

import(
    "fmt"
)


func main() {

	nextNumber := getSequence()

	fmt.Println(nextNumber())

}


func getSequence() func int {
    i := 0
    return func() int {
        i+=1
	return i
    }
}

