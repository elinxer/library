折腾了真久，GitHub commit之后，我手痒把本地的一个文件给删了，然后一直git pull都发现不能恢复。远程库里面还是有该文件的。就是我想将远程库的文件回到本地被删除了的位置。

特别的是，我在GitHub官网添加文件之后，pull之后会更新这个文件到本地。
但是那个被我收到删除的本地文件死活不恢复。



网上查了，事实上只有一篇真正提到使用checkout file，其余都是直接的复制，连个转载都不会说明。

确实：
F:\mygithub\javaprogram\util [master +0 ~0 -1]> git checkout
D       util/TestTimer.java

接下来只需要：
git checkout TestTimer.java
//上面的是你当前目录 是在util里面 不然会
//error: pathspec 'TestTimer.java' did not match any file(s) known to git.
//记得加入具体的路径

本以为在这一步解决的时候，发现文件还是没恢复。

可能是我删除之后又提交了一次的缘故。
后面在论坛里发现有个git reset --hard HEAD  将提交重置。
最后使用git checkout TestTimer.java 恢复过来了。

如果文件夹里面的文件都删除了，
那么只需要git checkout util(文件夹名)。

如果你不想折腾那么多，那直接地clone是一个挺好的选择。