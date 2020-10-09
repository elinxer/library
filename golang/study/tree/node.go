package tree

import "fmt"

/*

go没有构造函数

go语言退出函数后内部变量不一定被回收销毁

只有指针才可以改变结构体内容


nil指针也可以调用方法！！


值接收者 vs 指针接收者

1.要改变内容必须使用指针接收者

2.结构体过大也可以考虑使用指针接收者

3.一致性：如有指针接收者，最好都是指针接收者

4.值接收者是go特有的

5.值/指针接收者均可以接收值或指针，并不会规定调用者和使用方法的定义

6.扩展已有包的类型，别人的包我们不想改，但有要重新定义或加一个功能进去

定义别名，使用组合

*/

// 树节点结构
type TreeNode struct {
	Value       int
	Left, Right *TreeNode
}

// 结构体处理方法
// 接收者 (node TreeNode)  传值
// 函数名 print
// root.print()
func (node TreeNode) Print() {
	fmt.Println(node.Value, "")
}

// 传值的，不会改变外部
func (node TreeNode) setValue1(value int) {
	node.Value = value
}

// 直接接受者使用的是指针就好，其他指针也不用定义
// go会默认解释并返回，来调用方都不用在声明指针，直接用
func (node *TreeNode) SetValue(value int) {
	if node == nil {
		// 如果node是nil是不能再操作的，不然会报错，所以一定要return或保证不做操作
		fmt.Println("Setting value to nil node. Ignored.")
		return
	}
	node.Value = value
}

// 中序遍历: 左中右
func (node *TreeNode) Traverse() {

	// 重写的方式
	node.TraverseFunc(func(node *TreeNode) {
		node.Print()
	})
	fmt.Println()

	// if node == nil {
	// 	return
	// }
	// node.Left.Traverse()
	// node.Print()
	// node.Right.Traverse()

}

// 函数
func (node *TreeNode) TraverseFunc(f func(*TreeNode)) {
	if node == nil {
		return
	}
	node.Left.TraverseFunc(f)
	f(node)
	node.Right.TraverseFunc(f)
}

// 工厂函数实现构造
func CreateNode(value int) *TreeNode {
	// 返回局部变量的地址给外部用
	// 局部变量创建在哪里了呢？
	// 不需要知道！由go决定业务上是堆还是栈上
	return &TreeNode{Value: value}
}

func main1111() {

	// var root tree.TreeNode
	// root = TreeNode{Value: 3}
	// root.left = &TreeNode{}
	// root.right = &TreeNode{5, nil, nil}
	// root.right.left = new(TreeNode)
	// root.left.right = createNode(2)
	fmt.Println("test")

	// nodes := []TreeNode{
	// 	{value: 3},
	// 	{},
	// 	{6, nil, &root},
	// }

	// fmt.Println(nodes)
	// root.print()
	// root.right.left.setValue1(4)
	// root.right.left.print()

	// root.print()
	// root.setValue(100)

	// pRoot := &root
	// pRoot.print()
	// pRoot.setValue(200)
	// pRoot.print()
	// root.print()

	// var ppRoot *treeNode
	// ppRoot.setValue(200)
	// ppRoot = &root
	// ppRoot.setValue(300)
	// ppRoot.print()

	// root.traverse()

}
