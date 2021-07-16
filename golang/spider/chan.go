package main

import(
"fmt"
)


func main() {

    chan_str := make(chan string)


    chan_int := make(chan int)


    go func(){
    	chan_str <- "test content chane insert"
	chan_int <- 100
    }()

    _str := <- chan_str
	
    _int := <- chan_int

    fmt.Println(_str, "\n", chan_int, _int)

}


