# Redis 主从配置 搭建

#### 前提概要

Redis主从复制是直接通过tcp进行数据交流的，不是像mysql的文件复制。

Redis自带主从配置，可以直接实现，多机器为从，只读，master可写
- 主节点Master可读、可写.

- 从节点Slave只读。（read-only）

主从模型可以提高读的能力，在一定程度上缓解了写的能力。因为能写仍然只有Master节点一个， 可以将读的操作全部移交到从节点上，变相提高了写能力


##### 实现功能点

- Redis 主从复制

#### redis.conf 配置

**我们主要是体验搭建 Redis Sentinel,所以很多细枝末节的配置就没用添加进去，这个是基本的配置。**

在 redis/config 中新建以下配置

##### Redis 主从配置

- **Redis 主节点**： redis-7000.conf

  1.  新建 redis-7000.conf ：`cd config && touch redis-7000.conf`

  2. 修改配置 `vim redis-7000.conf`：

     ```
     # redis 启动端口
     port 7000
     # 以守护进程的方式启动
     daemonize yes
     # redis 的进程id
     pidfile /var/run/redis-7000.pid
     # redis 日志文件名
     logfile "redis-7000.log"
     # redis 数据存储位置自定义
     dir "/root/redis/data"
     # 开放外部访问
     bind 0.0.0.0
     ```

- **Redis 从节点1**：redis-7001.conf

  1. 新建 redis-7001.conf ：`cp redis-7000.conf redis-7002.conf`

  2. 修改配置 redis-7001.conf：

     ```
     # redis 启动端口
     port 7001
     # 以守护进程的方式启动
     daemonize yes
     # redis 的进程id
     pidfile /var/run/redis-7001.pid
     # redis 日志文件名
     logfile "redis-7001.log"
     # redis 数据存储位置自定义
     dir "/root/redis/data"
     # 开放外部访问
     bind 0.0.0.0
     # 主从复制
     slaveof 127.0.0.1 7000
     ```
- **Redis 从节点2**：redis-7002.conf

  1. 新建 redis-7002.conf 
    
        `cp redis-7001.conf redis-7002.conf`

  2. 修改配置 redis-7002.conf：

     ```
     # redis 启动端口
     port 7002
     # 以守护进程的方式启动
     daemonize yes
     # redis 的进程id
     pidfile /var/run/redis-7002.pid
     # redis 日志文件名
     logfile "redis-7002.log"
     # redis 数据存储位置自定义
     dir "/root/redis/data"
     # 开放外部访问
     bind 0.0.0.0
     # 主从复制
     slaveof 127.0.0.1 7000
     ```
  

##### 启动 redis 主从

启动命令：

```
cd /root/redis-5.0.8
./src/redis-server ./config/redis-7000.conf
./src/redis-server ./config/redis-7001.conf
./src/redis-server ./config/redis-7002.conf
```


```
redis-server redis-7000.conf
redis-server redis-7001.conf
redis-server redis-7002.conf
```

验证并查看主从信息:  `redis-cli -p 7000 info replication`

