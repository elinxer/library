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

// 定义小写首字母，不给包外使用了
type myTreeNode struct {
	// 这里使用指针是避免再拷贝一次TreeNode
	node *tree.TreeNode
}

// 实现这个结构MyTreeNode

func (myNode *myTreeNode) postOrder() {
	if myNode == nil || myNode.node == nil {
		return
	}
	left := myTreeNode{myNode.node.Left}
	left.postOrder()

	right := myTreeNode{myNode.node.Right}

	right.postOrder()

	myNode.node.Print()
}

func main() {

	var root tree.TreeNode
	root = tree.TreeNode{Value: 3}
	root.Left = &tree.TreeNode{}
	root.Right = &tree.TreeNode{5, nil, nil}
	root.Right.Left = new(tree.TreeNode)
	root.Left.Right = tree.CreateNode(2)

	root.Right.Left.SetValue(4)

	root.Traverse()
	fmt.Println()

	// 传入这个实体结构，关键是这里衔接的
	myRoot := myTreeNode{&root}
	myRoot.postOrder()
	fmt.Println()

	nodeCount := 0
	root.TraverseFunc(func (node *tree.TreeNode)  {
		nodeCount++
	})
	fmt.Println(nodeCount)
	
}
