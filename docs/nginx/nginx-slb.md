#### 概述

负载均衡（Server Load Balancer）是对多台服务器进行流量分发的负载均衡服务。
负载均衡可以通过流量分发扩展应用系统对外的服务能力，通过消除单点故障提升应用系统的可用性

SLB: Server Load Balancer
VIP：Virtual IP

本文基于keepalived实现虚拟IP自动转发绑定VIP，达到单点故障转移。

#### Keepalived 配置

**Keepalived IP**

slb 最少需要三个IP，两个负载均衡IP一个虚拟IP（公网可用）

负载均衡IP： 192.168.10.36 ， 192.168.10.39
web服务器IP： 192.168.10.40
vip（内网没用被使用ip）： 192.168.10.47 

>tips: VIP最好和负载均衡机IP在同一网段，如192.168.10.xxxx
> 如果不在同一个网段则要使用路由添加网段保持能与vip通信


虚拟机配置下注意

虚拟机搭建要注意IP段和网卡问题，一般都是选择桥接模式，这样主机可以自动分配一个内网IP给虚拟机，可以直接使用

在桥接下查看哪个网卡是主卡可以对外通信，如：eth1

查看ip命令： `ip a`


---

配置VIP时要注意设置运行外部通信：

配置VIP外部访问主从或双主模式需要三个公网IP，两个作keepalived负载，一个使用来路由转发到内网vip（网络路由转发）

转发VIP的外网IP将用来绑定域名解析


**keepalived配置**

```
docs/nginx/config/keepalived-master.conf
docs/nginx/config/keepalived-slave.conf
```






---

参考文章：

- [Nginx-keepalived+Nginx实现高可用集群](https://www.cnblogs.com/yanjieli/p/10682064.html#autoid-1-0-0)

- [ nginx + keepalived 主从模式](https://www.cnblogs.com/kevingrace/p/6138185.html)

- [LVS原理](http://www.178linux.com/13570)

- [keepalived的vip无法ping通排查过程](https://blog.csdn.net/wade1010/article/details/88863780)

- [vagrant 三种网络配置](https://www.jianshu.com/p/1aa9189598ef)

- [解决vagrant ssh登录时permission deny的问题](https://blog.csdn.net/weixin_34008805/article/details/91453210)

- [Windows10添加路由](https://blog.csdn.net/stpeace/article/details/41951405)

- [keepalived配置学习，解决vip无法ping通，虚拟服务器端口无法访问的问题](https://blog.csdn.net/charthyf/article/details/81456872)

- [keepalived的vip怎么 设置为公网IP？](https://www.zhihu.com/question/39595620/answer/126026530)

- [Vagrant Public Networks](https://www.vagrantup.com/docs/networking/public_network.html)

- [双机热备份-VRRP](https://www.jianshu.com/p/81115a4293c7)
