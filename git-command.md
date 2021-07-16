# Git常用命令指南

## Git仓库设置

创建一个新仓库

```
git clone git://github.com/elinxer/library.git
cd test
touch README.md
git add README.md
git commit -m "add README"
git push -u origin master
```

推送现有文件夹

```
cd existing_folder
git init
git remote add origin git://github.com/elinxer/library.git
git add .
git commit -m "Initial commit"
git push -u origin master
```

推送现有的 Git 仓库

```
cd existing_repo
git remote rename origin old-origin
git remote add origin git://github.com/elinxer/library.git
git push -u origin --all
git push -u origin --tags
```

更改原仓库地址

```
git remote set-url origin URL
```

远程仓库相关命令

```
检出仓库： git clone git://github.com/elinxer/library.git
查看远程仓库： git remote -v
添加远程仓库： git remote add [name] [url]
删除远程仓库： git remote rm [name]
修改远程仓库： git remote set-url --push [name] [newUrl]
拉取远程仓库： git pull [remoteName] [localBranchName]
推送远程仓库： git push [remoteName] [localBranchName]
```

## Git新仓库处理

**初始化本地仓库**

```
git init
```

**创建本地密匙key，用于远程仓库识别**

打开 git bash

```
ssh-keygen -t rsa -C "youremail@example.com"
```

将生成的秘钥配置到远程仓库中去，后续直接使用秘钥操作。

**本地配置远程仓库用户名和邮箱**

Git 全局设置

```
git config --global user.name "xxxx"
git config --global user.email "xxxx@xxx.com.cn"
```
默认仓库是空的，分支也不存在，需要手动提交一次代码到指定名称的仓库，一般会自动创建。

```
git add readme.md
git commit -m 'init'
git push -u origin main
```

## Git日常指令

**提交代码到远程库步骤**

- 可以先执行git pull命令远程命令来同步自己的本地仓库。
git pull
或者
git pull origin master
- 然后再
git push -u origin master

- 添加需要提交的文件
git add filename

- 批量添加
git add -A

- 添加当前目录所有文件
git add .

- 文件提交到本地仓库
git commit -m '本次提交说明';

- 查看github远程连接问题
ssh -T git@github.com

- 添加本地仓库到远程github库
git remote add origin https://github.com/elinxer/library.git
- 或者
git remote add origin git@github.com:elinxer/library.git

- git 可以使用git add -A 来获取本地所有修改变化，然后提交，可以跟远程仓库保持同步

- 查看提交/操作日志，如进入查看按q退出
git log 、
git reflog

- 查看git状态
git status [-s]

- 查看不同点
git diff

- 克隆远程库
git clone git://github.com/elinxer/library.git


## Git解决冲突问题

```
git stash 
git pull 
git stash pop

然后可以使用git diff -w +文件名 来确认代码自动合并的情况.

反过来,如果希望用代码库中的文件完全覆盖本地工作版本. 方法如下:

git reset --hard 
git pull

git 放弃本地修改 强制更新
git fetch --all
git reset --hard origin/master
git fetch 只是下载远程的库的内容，不做任何的合并 git reset 把HEAD指向刚刚下载的最新的版本

git merge：默认情况下，Git执行"快进式合并"（fast-farward merge），会直接将master分支指向dev分支。
使用--no-ff参数后，会执行正常合并，在Master分支上生成一个新节点。为了保证版本演进的清晰，建议采用这种方法。

git merge --no-ff dev
```

## 分支(branch)操作相关命令

```
查看本地分支：git branch
查看远程分支：git branch -r
创建本地分支：git branch [name] ----注意新分支创建后不会自动切换为当前分支
切换分支：git checkout [name]

创建新分支并立即切换到新分支：git checkout -b [name]

删除分支：git branch -d [name] ---- -d选项只能删除已经参与了合并的分支，对于未有合并的分支是无法删除的。如果想强制删除一个分支，可以使用-D选项

合并分支：git merge [name] ----将名称为[name]的分支与当前分支合并
创建远程分支(本地分支push到远程)：$ git push origin [name]

删除远程分支：git push origin :heads/[name] 或 gitpush origin :[name] 

如果想把本地的某个分支test提交到远程仓库，并作为远程仓库的master分支，或者作为另外一个名叫test的分支，如下：
git push origin test:master         // 提交本地test分支作为远程的master分支
git push origin test:test              // 提交本地test分支作为远程的test分支

创建空的分支：(执行命令之前记得先提交你当前分支的修改，否则会被强制删干净没得后悔)
git symbolic-ref HEAD refs/heads/[name]
rm .git/index
git clean -fdx
```

## 版本(tag)操作相关命令

```
查看版本： git tag
创建版本： git tag [name]
删除版本： git tag -d [name]
查看远程版本： git tag -r
创建远程版本(本地版本push到远程)： git push origin [name]
删除远程版本： git push origin :refs/tags/[name]
合并远程仓库的tag到本地： git pull origin --tags
上传本地tag到远程仓库： git push origin --tags
创建带注释的tag： git tag -a [name] -m 'yourMessage'
```

## 子模块(submodule)相关操作命令

```
添加子模块： 
git submodule add [url] [path]
如：
git submodule add git://github.com/elinxer/library.git src/main/webapp/ui-libs

初始化子模块： git submodule init  ----只在首次检出仓库时运行一次就行
更新子模块：git submodule update ----每次更新或切换分支后都需要运行一下
删除子模块：（分4步走哦）

 1) git rm --cached [path]
 2) 编辑“.gitmodules”文件，将子模块的相关配置节点删除掉
 3) 编辑“ .git/config”文件，将子模块的相关配置节点删除掉
 4) 手动删除子模块残留的目录
 5）忽略一些文件、文件夹不提交

在仓库根目录下创建名称为“.gitignore”的文件，写入不需要的文件夹名或文件，每个元素占一行即可，如
target
bin
*.db 
```

## Git常用操作命令大全

```
git branch 查看本地所有分支
git status 查看当前状态 
git commit 提交 
git branch -a 查看所有的分支
git branch -r 查看本地所有分支
git commit -am "init" 提交并且加注释 
git remote add origin git@192.168.1.119:ndshow
git push origin master 将文件给推到服务器上 
git remote show origin 显示远程库origin里的资源 
git push origin master:develop
git push origin master:hb-dev 将本地库与服务器上的库进行关联 
git checkout --track origin/dev 切换到远程dev分支
git branch -D master develop 删除本地库develop
git checkout -b dev 建立一个新的本地分支dev
git merge origin/dev 将分支dev与当前分支进行合并
git checkout dev 切换到本地dev分支
git remote show 查看远程库
git add .
git rm 文件名(包括路径) 从git中删除指定文件
git clone git://github.com/elinxer/library.git 从服务器上将代码给拉下来
git config --list 看所有用户
git ls-files 看已经被提交的
git rm [file name] 删除一个文件
git commit -a 提交当前repos的所有的改变
git add [file name] 添加一个文件到git index
git commit -v 当你用－v参数的时候可以看commit的差异
git commit -m "This is the message describing the commit" 添加commit信息
git commit -a -a是代表add，把所有的change加到git index里然后再commit
git commit -a -v 一般提交命令
git log 看你commit的日志
git diff 查看尚未暂存的更新
git rm a.a 移除文件(从暂存区和工作区中删除)
git rm --cached a.a 移除文件(只从暂存区中删除)
git commit -m "remove" 移除文件(从Git中删除)
git rm -f a.a 强行移除修改后文件(从暂存区和工作区中删除)
git diff --cached 或 $ git diff --staged 查看尚未提交的更新
git stash push 将文件给push到一个临时空间中
git stash pop 将文件从临时空间pop下来
---------------------------------------------------------
git remote add origin git://github.com/elinxer/library.git
git push origin master 将本地项目给提交到服务器中
-----------------------------------------------------------
git pull 本地与服务器端同步
-----------------------------------------------------------------
git push (远程仓库名) (分支名) 将本地分支推送到服务器上去。
git push origin serverfix:awesomebranch
------------------------------------------------------------------
git fetch 相当于是从远程获取最新版本到本地，不会自动merge
git commit -a -m "log_message" (-a是提交所有改动，-m是加入log信息) 本地修改同步至服务器端 ：
git branch branch_0.1 master 从主分支master创建branch_0.1分支
git branch -m branch_0.1 branch_1.0 将branch_0.1重命名为branch_1.0
git checkout branch_1.0/master 切换到branch_1.0/master分支
du -hs

-----------------------------------------------------------
mkdir WebApp
cd WebApp
git init
touch README
git add README
git commit -m 'init'
git remote add git://github.com/elinxer/library.git
git push -u origin master

```
