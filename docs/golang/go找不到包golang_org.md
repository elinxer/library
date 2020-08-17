@[TOC](go编译找不到包golang.org/x/net)

# 运行环境

go 版本：go version go1.14.4 windows/amd64

使用包：net/http

```
import (
	"fmt"
	"net/http"
)

```

## 编译发生错误

在安装自定义包时无法获取包

```
go get -u github.com/jackdanger/collectlinks
unrecognized import path "golang.org/x/net/html": https fetch: Get "https://golang.org/x/net/html?go-get=1": dial tcp 216.239.37.1:443: connectex: A connection attempt failed because the connected party did not properly respond after a period of time, or establishe
d connection failed because connected host has failed to respond.

```
编译失败错误
```
cannot find package "golang.org/x/net/html" in any of:
	C:\Go\src\golang.org\x\net\html (from $GOROOT)
	E:\develop\zhiteer\library\golang\src\golang.org\x\net\html (from $GOPATH)
	C:\Users\Administrator\go\src\golang.org\x\net\html
```
## 问题原因

嗯，，，，被墙了，这个网站，golang.org



## 解决办法
检查你的项目路径，并明确go安装的位置，如我的：`C:\Users\Administrator\go`

则在go安装目录下会存在src目录，进入src目录。

命令行输入命令：

```
git clone https://github.com/golang/net ./
```

将包直接下载在当前目录下即可。

下载完成时完整结构：

`C:\Users\Administrator\go\src\golang.org\x\net`

因此，您也可以直接去官方github直接下载压缩包，然后解压到src目录下来，目录路径保持一致即可。

