package main

// 注意别写错路径和单词！
// C:\Users\Administrator\go 是win10go默认安装的gopath地址
// 可以查看：go env
// 可以修改该gopath为自己定义也可以
// C:\Users\Administrator\go\src\libray\golang\study\tree

import (
	"fmt"
	"library/golang/study/tree"
)

func main() {

	var root tree.TreeNode
	root = tree.TreeNode{Value: 3}
	root.Left = &tree.TreeNode{}
	root.Right = &tree.TreeNode{5, nil, nil}
	root.Right.Left = new(tree.TreeNode)
	root.Left.Right = tree.CreateNode(2)
	fmt.Println(root)

}
