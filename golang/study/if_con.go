package main

import (
	"fmt"
	"io/ioutil"
)

func main() {

	const filename = "abc.txt"

	contents, err := ioutil.ReadFile(filename)

	if err != nil {
		fmt.Println(err)
	} else {
		fmt.Printf("%s\n", contents)
	}

	if	contents2, err2 := ioutil.ReadFile(filename); err2 != nil {
		 
	}

	
}
