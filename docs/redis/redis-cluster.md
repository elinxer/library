### redis cluster 集群

redis sentinel哨兵主要是单一主多从的方式，缺陷还是只有一个写，这样适用于缓存不是非常巨大的业务，而缓存并发和存储过大就不适合了，如超过50以上

这时候就要用到cluster分片集群，可以有多个写，并且存在的缓存散列分布在各个节点，降低存储压力和成本。


#### 前提概要

redis集群最低要6个节点，不然无法创建

redis 安装目录在root用户下home目录，等于实际路径： /root/redis-5.0.8

> 首先我们既然要搭建集群，那么master节点至少要3个，slave节点也是3个
  为什么呢？这是因为一个redis集群如果要对外提供可用的服务，那么集群中必须要有过半的master节点正常工作。
  基于这个特性，如果想搭建一个能够允许 n 个master节点挂掉的集群，那么就要搭建2n+1个master节点的集群


#### 创建第一个配置文件

共需要6个配置

进入你定义的配置目录，创建第一个配置文件，内容如下：

文件名： `redis-cluster-7100.conf`

```
#端口号
port 7100
#开启后台运行
daemonize yes
#设置 Redis 实例 pid 文件
pidfile /var/run/redis_7100.pid
#绑定本机IP
bind 0.0.0.0
#设置密码
requirepass 123456
#节点内部互相访问的密码，如果节点设置了密码，这项一定要配，否则不会主从复制和宕机选举
masterauth 123456
#启用集群模式
cluster-enabled yes
#设置当前节点集群配置文件路径
cluster-config-file nodes-7100.conf
#设置当前节点连接超时毫秒数
cluster-node-timeout 15000

```

#### 创建另外5个配置

以下命令可以直接替换某个数字：

```

sed 's/7100/7101/g' redis-cluster-7100.conf > redis-cluster-7101.conf
sed 's/7100/7102/g' redis-cluster-7100.conf > redis-cluster-7102.conf
sed 's/7100/7103/g' redis-cluster-7100.conf > redis-cluster-7103.conf
sed 's/7100/7104/g' redis-cluster-7100.conf > redis-cluster-7104.conf
sed 's/7100/7105/g' redis-cluster-7100.conf > redis-cluster-7105.conf

```

#### 手动启动这6个节点

```

/root/redis-5.0.8/src/redis-server /root/redis-5.0.8/config/redis-cluster-7100.conf
/root/redis-5.0.8/src/redis-server /root/redis-5.0.8/config/redis-cluster-7101.conf
/root/redis-5.0.8/src/redis-server /root/redis-5.0.8/config/redis-cluster-7102.conf
/root/redis-5.0.8/src/redis-server /root/redis-5.0.8/config/redis-cluster-7103.conf
/root/redis-5.0.8/src/redis-server /root/redis-5.0.8/config/redis-cluster-7104.conf
/root/redis-5.0.8/src/redis-server /root/redis-5.0.8/config/redis-cluster-7105.conf

```

#### 一键启动集群

命令：

```
/root/redis-5.0.8/src/redis-cli --cluster create 127.0.0.1:7100 127.0.0.1:7101 \
127.0.0.1:7102 127.0.0.1:7103 127.0.0.1:7104 127.0.0.1:7105 \
--cluster-replicas 1 -a 123456

```

如果创建集群时设置slave为1个

--cluster-replicas 1

注意如果设置了集群节点密码需要参数 -a 指定通用密码，否则报错：

```
[ERR] Node 127.0.0.1:7100 NOAUTH Authentication required.
```

```
Warning: Using a password with '-a' or '-u' option on the command line interface may not be safe.
>>> Performing hash slots allocation on 6 nodes...
Master[0] -> Slots 0 - 5460
Master[1] -> Slots 5461 - 10922
Master[2] -> Slots 10923 - 16383
Adding replica 127.0.0.1:7104 to 127.0.0.1:7100
Adding replica 127.0.0.1:7105 to 127.0.0.1:7101
Adding replica 127.0.0.1:7103 to 127.0.0.1:7102
>>> Trying to optimize slaves allocation for anti-affinity
[WARNING] Some slaves are in the same host as their master
M: 827230b12e6f2a1363ce9aa1bd111a2bea03ae49 127.0.0.1:7100
   slots:[0-5460] (5461 slots) master
M: c08b9df381f1ee1d8686f0fc1426be983d8207c1 127.0.0.1:7101
   slots:[5461-10922] (5462 slots) master
M: 504039e042e0bff87bbb5614be69003e955d5533 127.0.0.1:7102
   slots:[10923-16383] (5461 slots) master
S: 21077282c9723c4df61d735b177aa14f2078d01d 127.0.0.1:7103
   replicates c08b9df381f1ee1d8686f0fc1426be983d8207c1
S: 0029311951464878525f659d91fae91ee3a1d529 127.0.0.1:7104
   replicates 504039e042e0bff87bbb5614be69003e955d5533
S: 2092089878ff7783924831702477392d78a28f7e 127.0.0.1:7105
   replicates 827230b12e6f2a1363ce9aa1bd111a2bea03ae49
Can I set the above configuration? (type 'yes' to accept): yes
```

```
>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join
......
>>> Performing Cluster Check (using node 127.0.0.1:7100)
M: 827230b12e6f2a1363ce9aa1bd111a2bea03ae49 127.0.0.1:7100
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
M: c08b9df381f1ee1d8686f0fc1426be983d8207c1 127.0.0.1:7101
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
M: 504039e042e0bff87bbb5614be69003e955d5533 127.0.0.1:7102
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
S: 2092089878ff7783924831702477392d78a28f7e 127.0.0.1:7105
   slots: (0 slots) slave
   replicates 827230b12e6f2a1363ce9aa1bd111a2bea03ae49
S: 0029311951464878525f659d91fae91ee3a1d529 127.0.0.1:7104
   slots: (0 slots) slave
   replicates 504039e042e0bff87bbb5614be69003e955d5533
S: 21077282c9723c4df61d735b177aa14f2078d01d 127.0.0.1:7103
   slots: (0 slots) slave
   replicates c08b9df381f1ee1d8686f0fc1426be983d8207c1
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
```

输入创建集群的命令后会出现以下提示，注意Can I set the above configuration? (type 'yes' to accept): yes，该处请输入yes，不然分配不了哈希槽


### 验证集群是否成功

登录一个redis，然后使用命令查看集群节点： `cluster nodes`

流程和命令详细如下：

```
# -c 代表集群模式连接
/root/redis-5.0.8/src/redis-cli -c -p 7100

[root@localhost config]# /root/redis-5.0.8/src/redis-cli -c -p 7100
127.0.0.1:7100> auth 123456
OK
127.0.0.1:7100> cluster nodes
c08b9df381f1ee1d8686f0fc1426be983d8207c1 127.0.0.1:7101@17101 master - 0 1586846269000 2 connected 5461-10922
504039e042e0bff87bbb5614be69003e955d5533 127.0.0.1:7102@17102 master - 0 1586846271342 3 connected 10923-16383
2092089878ff7783924831702477392d78a28f7e 127.0.0.1:7105@17105 slave 827230b12e6f2a1363ce9aa1bd111a2bea03ae49 0 1586846270000 6 connected
827230b12e6f2a1363ce9aa1bd111a2bea03ae49 127.0.0.1:7100@17100 myself,master - 0 1586846270000 1 connected 0-5460
0029311951464878525f659d91fae91ee3a1d529 127.0.0.1:7104@17104 slave 504039e042e0bff87bbb5614be69003e955d5533 0 1586846270335 5 connected
21077282c9723c4df61d735b177aa14f2078d01d 127.0.0.1:7103@17103 slave c08b9df381f1ee1d8686f0fc1426be983d8207c1 0 1586846272349 4 connected
127.0.0.1:7100> set test1111 1
OK
127.0.0.1:7100> exit
[root@localhost config]# /root/redis-5.0.8/src/redis-cli -c -p 7102
127.0.0.1:7102> auth 123456
OK
127.0.0.1:7102> get test1111
-> Redirected to slot [4670] located at 127.0.0.1:7100
(error) NOAUTH Authentication required.
127.0.0.1:7100> auth 123456
OK
127.0.0.1:7100> get test1111
"1"
127.0.0.1:7100>
```

以上可以看到，在7100节点设置了缓存信息，然后在7102获取，此时会转换到7100节点，并要求输入密码，
此时需要再通过命令获取到想要的缓存，也就是说，每个节点间会记录下key设置时所处的槽点，在别的槽点（节点）
获取会自动转发过去，然后需要客服端重新获取缓存。

