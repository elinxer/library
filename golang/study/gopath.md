### 说明


gopath可以设置多个linux设置冒号分割，win10分号分割开来就行;

win10

```
%USERPROFILE%\go;H:\zhiteer.com
```

最好在系统层定义，这样可以直接识别，不要在编辑器或IDE定义，不用去折腾。


### gopm


gopm绕过防火墙


下载

-v 显示更多信息, 因为是github仓库，需要安装git


`go get -v github.com/gpmgo/gopm`


默认会安装在第一个gopath下
 ```
C:\Users\Administrator\go\src\github.com
 ```

 同时也加入了go bin中，可以直接运行gopm helo

下载被墙的包

```
gopm get -g -v golang.org/x/tools/cmd/goimports
```


下载了goimports包，然后build这个包

这会安装在当前目录
go build golang.org/x/tools/cmd/goimports



安装到指定目录
自动build，并安装到bin目录
```
go install golang.org/x/tools/cmd/goimports
```


然后插件配置这个目录即可


---


go build 编译

go install 产生pkg可执行编译文件



src是自己的目录

pkg build的中间过程

bin可执行文件

