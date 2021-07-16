//+build ingore
package main

import (
	"fmt"
)

/**

包导入的4中方式：

1. 通用导入方式

直接导入包名

import (
   "fmt"
）

换行是默认语法推荐方式，可以简洁添加多个，并适用IDE格式化

然后我们就可以通过包名调用相应的函数或者变量了

fmt.Println("Hello World!")

2. 点 import

点import就是在import 的包前面加个".", 这个导入方式，在使用相应的包的函数或者变量的时候，可以省略包名。

import (
   . "fmt"
)

然后我们可以不用带包名前缀调用相应包的函数，该方式主要包使用重复或正确的函数，防止错误和冲突

Println("Hello World!")


3. 别名import

有时候可能包的名字很长或者不容易记忆，然后你可以给这个包起个别名（比如短一点啊或者容易记忆啊）

import(
	f "fmt"
)

其中f 为包fmt的别名，可以直接使用

f.Println("Hello World!")

4.下划线import

当我们import一个包的时候，它里面的所有init()函数都会被执行，但是有时候我们并不真正需要使用这些包，仅仅是希望它里面的init()函数被执行，这个时候，就可以使用下划线import了

import (
   "database/sql"
    _ "github.com/go-sql-driver/mysql"
)

下划线导入属于导入并执行初始化的功能作用

如果导入项目下自建目录包（非加入了go管理包）：

import (
	_ "./demo/imp"
	"fmt"
)

附加：

导入的包里不能带有 main 函数，否则会报错


*/

func main() {
	fmt.Println("Test")
}
