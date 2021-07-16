# Redis5最新版本搭建

Redis编译搭建，采用最新版本编译安装，系统版本centos7.2+

### 运行环境

- centos7.2 (本次是单机搭建，生产环境需要多台物理机)

- Redis 5.0.8

### 环境安装

#### centos7安装

自行安装centos 7 系统 这里就不在说明了。

#### 安装Redis

1.  下载 Redis ，现在的最新版本为 5.0.8。当前是在`/usr`下

   - 选好安装目录和确定root或安装用户（组） `cd ~`
   
   - 下载： `wget http://download.redis.io/releases/redis-5.0.8.tar.gz`

2. 解压安装包

   - 解压：`tar -xvf redis-5.0.8.tar.gz`

3. 建立软连接

   - 原因：方便管理和升级

   - 建立软连接：`ln -s redis-5.0.8 redis`


4. 编译

   - 进入 redis ，执行编译。命令：`cd redis`, 然后直接输入： `make`。

   - <font color='red'>注意：这里可能会出现错误，如果无法编译请查看本文最后的 “可能出现的问题” 并寻找解决方法</font>。

5. 安装

   - 安装：`make install`

6.  启动

    1.  启动方式1：`redis-server` , 默认端口为 6379

    2.  启动方式2：`redis-server --port 6380`,指定端口启动

    3.  启动方式3：通过配置文件启动，也是我们推荐的启动方式，方便管理 
    
        - 命令 `redis-server redis-xxxx.conf`

        - 在安装的redis目录中新建 data 文件。`mkdir data` 方便我们存储数据
        
        - 在安装的redis目录中新建 config 文件。`mkdir config`
        
        - 复制 redis 目录中 redis.conf 文件到 config/ 目录中。`cp redis.conf ./config`
  
        - 进入 config 目录。`cd config`


---

redis编译安装完成

#### redis 常用配置+命令


修改可以远程访问： ` bind 0.0.0.0 `

修改密码： `requirepass 123456`

守护进程启动： `daemonize yes`


**启动Redis**

```
# 命令+指定配置
/root/redis-5.0.8/src/redis-server /root/redis-5.0.8/config/redis-cluster-7000.conf
```

**进入Redis命令行**

登录Redis并使用密码`-a`

```
redis-cli -h xxxx.xx.xx.xx -p 6739 -a
```
