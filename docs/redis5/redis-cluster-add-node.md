# Redis Cluster 集群扩容

>本文基于：【Redis Cluster 集群】
>

新增两个节点，一主一副

以 7000为主，7001为副


#### 新增集群节点

```
sed 's/7100/7000/g' redis-cluster-7100.conf > redis-cluster-7000.conf
sed 's/7100/7001/g' redis-cluster-7100.conf > redis-cluster-7001.conf

```

#### 启动节点

```
/root/redis-5.0.8/src/redis-server /root/redis-5.0.8/config/redis-cluster-7000.conf
/root/redis-5.0.8/src/redis-server /root/redis-5.0.8/config/redis-cluster-7001.conf
/root/redis-5.0.8/src/redis-server /root/redis-5.0.8/config/redis-cluster-7002.conf
```

### 加入集群节点 方法一

- 添加主节点和从节点

访问集群： ` /root/redis-5.0.8/src/redis-cli -c -p 7100 -a 123456 `

redis-cli里面输入命令： `cluster nodes`

获取新添加的节点ID： `ce91c6305206b7149efabeef46246cef9642fd65`

以下三种方式为添加节点的方式说明：

- 添加主节点

127.0.0.1:7000 为待添加主节点， 127.0.0.1:7100 随机一个已存在主节点

-a 表示访问密码

```
/root/redis-5.0.8/src/redis-cli --cluster add-node 127.0.0.1:7000 127.0.0.1:7100 -a 123456
```

- 将新节点添加为副本

```
/root/redis-5.0.8/src/redis-cli --cluster add-node 127.0.0.1:7000 127.0.0.1:7100 --cluster-slave -a 123456
```

- 将节点添加为副本并指定主数据库

```
/root/redis-5.0.8/src/redis-cli redis-cli --cluster add-node 127.0.0.1:7000 127.0.0.1:7100 --cluster-slave --cluster-master-id 3c3a0c74aae0b56170ccb03a76b60cfe7dc1912e -a 123456
```

#### 重新分片

开始重新分片

```
/root/redis-5.0.8/src/redis-cli --cluster reshard 127.0.0.1:7000 -a 123456
```

#### 设置从节点

```
/root/redis-5.0.8/src/redis-cli --cluster add-node 127.0.0.1:7001 127.0.0.1:7100 -a 123456
```

登录新加的节点： ` /root/redis-5.0.8/src/redis-cli -c -p 7001 -a 123456 `

直接指定父级(上一个)节点： 

```
cluster replicate ce91c6305206b7149efabeef46246cef9642fd65
```

### 加入集群节点 方法二

直接为指定主节点加入从节点

```
/root/redis-5.0.8/src/redis-cli --cluster add-node 127.0.0.1:7002 127.0.0.1:7100 --cluster-slave --cluster-master-id ce91c6305206b7149efabeef46246cef9642fd65 -a 123456
```

查看主从

```
/root/redis-5.0.8/src/redis-cli -p 7000 -a 123456 cluster nodes | grep slave | grep ce91c6305206b7149efabeef46246cef9642fd65 
```


### 删除节点

- 删除一个从节点

第一个参数只是集群中的一个随机节点，第二个参数是您要删除的节点的ID。

127.0.0.1:7000 随机一个已存在的节点，为啥可以是随机的呢，只是方便登录进入集群进行操作

```
/root/redis-5.0.8/src/redis-cli --cluster del-node 127.0.0.1:7000 e61a8bfb814c3988f88467807ab9a1b78000425c
```

- 删除一个主节点

清空主节点所有缓存，直接执行命令：

```
/root/redis-5.0.8/src/redis-cli --cluster del-node 127.0.0.1:7000 主节点ID -a 123456
```

您也可以用相同的方法删除主节点，但是要删除主节点，主节点内容必须为空，如果主节点不为空，则需要先将数据从其重新分片到所有其他主节点。

删除主节点的另一种方法是在其从节点之一上对其执行手动故障转移，并在该节点成为新主节点的从节点之后将其删除。显然，这在您要减少群集中的主节点的实际数量时无济于事，在这种情况下，需要重新分片。

手动故障转移： 如一个主的要进行删除，可以直接杀掉主redis，集群会自动转移到从库使用

参考文章：

https://blog.51cto.com/phpme/2447995

https://blog.csdn.net/iteen/article/details/102718048

https://redis.io/topics/cluster-tutorial
