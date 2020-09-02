//+build ingore
package main

import (
	"fmt"
	"time"
)

// 查看 goroutine内部错误
// go run -race goroutine.go

func main() {

	for i := 0; i < 10; i++ {

		go func(i int) {
			fmt.Printf("goroutine: %d\n", i)
		}(i)

	}

	time.Sleep(time.Millisecond)

}
