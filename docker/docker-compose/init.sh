#!/bin/sh

# set docker-compose alias
if ! grep "alias dc='docker-compose'" /etc/profile &> /dev/null
then
cat>>/etc/profile<<EOF 

alias dc='docker-compose'

EOF
source /etc/profile
dc -version
fi

# set elasticsearch env
# https://www.elastic.co/guide/en/elasticsearch/reference/6.5/docker.html#docker-prod-cluster-composefile
if ! grep vm.max_map_count /etc/sysctl.conf &> /dev/null
then
cat>>/etc/sysctl.conf<<EOF 

vm.max_map_count=262144

EOF
sysctl -w vm.max_map_count=262144
echo set vm.max_map_count success!
fi

# 创建数据文件夹
if [ ! -d /data/docker_file ]; then
mkdir -p /data/docker_file
chmod 777 /data/docker_file
echo data dir create success!
fi

############################## DNS Config Start ###############################
dns_conf_dir=/data/docker_file/dns/config/
dns_conf_file=${dns_conf_dir}dnsmasq.conf

if ! grep server ${dns_conf_file} &> /dev/null
then
mkdir -p ${dns_conf_dir}
#wget http://oss.segetech.com/intra/srv/dnsmasq.conf
#cp dnsmasq.conf ${dns_conf_file}
#rm -rf dnsmasq.conf
cat>>${dns_conf_file}<<EOF 

#输出查询日志信息
# For debugging purposes, log each DNS query as it passes through dnsmasq.
log-queries

#默认会使用网关server,若需要配置本地局域网自动使用使用本DNS服务，则须将此选项设置为NO
# If you don't want dnsmasq to read /etc/resolv.conf or any other file, getting its servers from this file instead (see below), then uncomment this.
no-resolv 

#配置使用的服务，即本地查询不到时，可通过此服务依次进行查询解析，可配置多个，一般为已知的或代理的外网DNS服务
# Example of routing PTR queries to nameservers: this will send all 
# address->name queries for 192.168.3/24 to nameserver 10.1.2.3
server=114.114.114.114
server=8.8.8.8

#配置域名对应的ip地址，可配置多个
# Add domains which you want to force to an IP address here.
# The example below send any host in doubleclick.net to a local webserver.
#address=/hostname1/192.168.248.136
#address=/hostname2/192.168.248.137

# Include a another lot of configuration options.
conf-dir=/etc/dnsmasq.d

EOF
fi
############################## DNS Config END ###############################

############################## Nginx Config Start ###############################
nginx_conf_home=/data/docker_file/nginx/conf.d
nginx_root_file=${nginx_conf_home}/root.conf

if [ ! -d ${nginx_conf_home} ]; then
mkdir -p ${nginx_conf_home}
echo nginx conf dir create success!
fi

if ! grep server ${nginx_root_file} &> /dev/null
then
cat>>${nginx_root_file}<<EOF 

server {
    listen       80;

    # 默认页面
    location / {
        root   /usr/share/nginx/html;
        index  index.html index.htm;
    }
    # 错误页
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }
}
EOF
echo set nginx root.conf create success!
fi

############################## Nginx Config Start ###############################


############################## Redis Cluster Config Start ###############################

# 定义Redis-Cluster初始化函数
initRedisConfigFile(){
redis_port=$1
config_home=/data/docker_file/redis-cluster/node_${redis_port}/config

# 创建文件夹
if [ ! -d ${config_home} ]; then
    mkdir -p ${config_home}
    echo config dir ${config_home} create success!
fi

# 初始化文件内容
config_file=${config_home}/redis.conf
# 无脑重置配置文件内容
# rm -rf ${config_file}
if ! grep port ${config_file} &> /dev/null
then
cat>>${config_file}<<EOF 

# http://download.redis.io/redis-stable/redis.conf

#端口
port ${redis_port}

#pid
pidfile /var/run/redis_${redis_port}.pid 

# 绑定，注释掉，防止无法访问redis
# bind 127.0.0.1

#开启集群
cluster-enabled yes

#集群配置文件
cluster-config-file nodes_${redis_port}.conf
cluster-node-timeout 5000

#更新操作后进行日志记录
appendonly yes

#设置主服务的连接密码
#主要是针对master对应的slave节点设置的，在slave节点数据同步的时候用到
masterauth root123

#设置从服务的连接密码
#对登录权限做限制，redis每个节点的requirepass可以是独立、不同的
requirepass password123

EOF
echo init redis conf file ${config_file} success!
fi
}

initRedisConfigFile 6061
initRedisConfigFile 6062
initRedisConfigFile 6063
initRedisConfigFile 6064
initRedisConfigFile 6065
initRedisConfigFile 6066

############################### Redis Cluster Config End ################################

echo finished!
