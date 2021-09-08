# docker-compose

> 项目主要收集使用docker-compose来快速构建开发环境
> https://github.com/cookcodeblog (可以参考)


## docker 常用命令介绍

## docker-compose 常用命令介绍

## docker-compose 安装环境

### 一、安装docker-compose 


### 二、安装portainer

1. docker方式安装

```bash
docker run -d -p 9000:9000 --restart=always --name portainer -v /var/run/docker.sock:/var/run/docker.sock -v /data/docker_file/portainer/data:/data docker.io/portainer/portainer
```

2. docker-compose方式安装

```yaml
version: "3"
services:
  portainer:
    image: docker.io/portainer/portainer
    container_name: portainer
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
      - /data/docker_file/portainer/data:/usr/share/elasticsearch/data
    restart: always
    ports:
      - 9000:9000
```

### 三、安装mysql

1. docker-compose方式安装

```yml
version: '3'
services:
  # ref：https://hub.docker.com/_/mysql
  # ref：https://docs.docker.com/samples/library/mysql/#-via-docker-stack-deploy-or-docker-compose
  mysql:
    #image: mysql:5.7.22
    image: mysql:8.0
    container_name: mysql
    command: 
      --default-authentication-plugin=mysql_native_password
      --character-set-server=utf8mb4
      --collation-server=utf8mb4_general_ci
      --explicit_defaults_for_timestamp=true
      --lower_case_table_names=1
    # data 用来存放了数据库表文件，init存放初始化的脚本
    volumes:
      - /data/docker_file/mysql/data/:/var/lib/mysql/
      - /data/docker_file/mysql/conf/my.cnf:/etc/my.cnf
      - /data/docker_file/mysql/init:/docker-entrypoint-initdb.d/
    restart: always
    ports:
      - "3306:3306"
      - "33060:33060"
    environment:
      TZ: Asia/Shanghai
      MYSQL_ROOT_PASSWORD: root123
      MYSQL_USER: dev
      MYSQL_PASSWORD: dev123
```

### 四、安装redis

1. docker方式安装单机版redis
`docker run --name redis -p 6379:6379 -d --restart=always redis:latest redis-server --appendonly yes --requirepass "your passwd"`

2. docker-compose方式安装单机版redis

```yml
version: '3'
services:
  redis:
    image: redis
    container_name: redis
    # 优先使用命令行参数，期次是redis.conf中的参数
    command: redis-server /usr/local/etc/redis/redis.conf  --requirepass "root123"
    restart: always
    volumes:
      - /data/docker_file/redis/data:/data
      - /data/docker_file/redis/data/redis.conf:/usr/local/etc/redis/redis.conf
    ports:
      - "6379:6379"
```

3. docker-compose方式安装集群版redis



### 五、安装rabbitmq

1. docker-compose方式安装单机版rabbitmq

```yml
version: '3'
services:
  rabbitmq:
    hostname: rabbitmq-standalone
    image: rabbitmq:management-alpine
    container_name: rabbitmq
    environment:
      - RABBITMQ_DEFAULT_USER=root
      - RABBITMQ_DEFAULT_PASS=root123
    restart: always
    volumes:
      - /data/docker_file/rabbitmq/data:/var/lib/rabbitmq
    ports:
      - "15672:15672"
      - "5672:5672"
    logging:
      driver: "json-file"
      options:
        max-size: "200k"
        max-file: "10"
```

2. docker-compose方式安装集群版rabbitmq

```yml
version: '3'
services:
  # ref: https://github.com/bijukunjummen/docker-rabbitmq-cluster
  rabbitmq1:
    image: bijukunjummen/rabbitmq-server:3.7.0
    hostname: rabbitmq-cluster-node1
    environment:
      - RABBITMQ_DEFAULT_USER=root
      - RABBITMQ_DEFAULT_PASS=root123
    ports:
      - "5672:5672"
      - "15672:15672"
  rabbitmq2:
    image: bijukunjummen/rabbitmq-server:3.7.0
    hostname: rabbitmq-cluster-node2
    links:
      - rabbitmq1
    environment: 
     - CLUSTERED=true
     - CLUSTER_WITH=rabbitmq1
     - RAM_NODE=true
    ports:
        - "5673:5672"
        - "15673:15672"
  rabbitmq3:
    image: bijukunjummen/rabbitmq-server:3.7.0
    hostname: rabbitmq-cluster-node3
    links:
      - rabbitmq1
      - rabbitmq2
    environment: 
      - CLUSTERED=true
      - CLUSTER_WITH=rabbitmq1
    ports:
      - "5674:5672"
```

### 六、安装mongodb

```yml
version: '3'
services:
  mongo:
    image: mongo:4.1.6
    ports:
      - 27017:27017
    restart: always
    volumes:
      - /data/docker_file/mongodb/data/db:/data/db
    environment:
      MONGO_INITDB_ROOT_USERNAME: root
      MONGO_INITDB_ROOT_PASSWORD: root123
  # mongo可视化管理工具，按需安装
  mongo-express:
    links:
      - mongo
    image: mongo-express
    restart: always
    ports:
      - 9090:8081
    environment:
      ME_CONFIG_OPTIONS_EDITORTHEME: 3024-night
      ME_CONFIG_BASICAUTH_USERNAME: mongo
      ME_CONFIG_BASICAUTH_PASSWORD: mongo
      ME_CONFIG_MONGODB_ADMINUSERNAME: root
      ME_CONFIG_MONGODB_ADMINPASSWORD: root123
```


### 七、安装consul

1. docker-compose方式安装单机版consul

```yml
version: '3'
services:
  consul:
    image: consul:latest
    container_name: consul
    volumes:
      - /data/docker_file/consul/data:/consul/data
      - /data/docker_file/consul/config:/consul/config
    ports:
      - 8500:8500
    command: agent -server -bind=0.0.0.0 -client=0.0.0.0 -node=consul_Server1 -bootstrap-expect=1 -ui
```


2. docker-compose方式安装集群版consul (带acl_token)

   - 创建文件夹：/data/docker_file/consul_cluster/

   - 创建文件：acl-server.json

      ```json
      {
          "acl_datacenter": "dc1",
          "acl_master_token": "16c5413b-276c-45ab-9b0e-9664126f1161",
          "acl_default_policy": "deny"
      }
      ```

   - 创建文件：acl-client.json

      ```json
      {
        "acl_datacenter": "dc1",
        "acl_token": "16c5413b-276c-45ab-9b0e-9664126f1161"
      }
      ```



以上操作脚本：

```bash
mkdir -p /data/docker_file/consul_cluster
touch /data/docker_file/consul_cluster/acl-client.json
touch /data/docker_file/consul_cluster/acl-server.json
cat>>/data/docker_file/consul_cluster/acl-server.json<<EOF 
{
          "acl_datacenter": "dc1",
          "acl_master_token": "16c5413b-276c-45ab-9b0e-9664126f1161",
          "acl_default_policy": "deny"
}
EOF
cat>>/data/docker_file/consul_cluster/acl-client.json<<EOF 
{
        "acl_datacenter": "dc1",
        "acl_token": "16c5413b-276c-45ab-9b0e-9664126f1161"
}
EOF
```



docker-compose.yml：

```yml
version: '3'
services:
  # ref: https://blog.csdn.net/qq_24384579/article/details/86480522
  consul_server1:
    image: consul:latest
    container_name: consul_server1
    restart: always
    command: agent -server -client=0.0.0.0 -bootstrap-expect=3 -node=consul_server1 -config-dir=/home/acl.json
    volumes:
      - /data/docker_file/consul_cluster/acl-server.json:/home/acl.json
  consul_server2:
    image: consul:latest
    container_name: consul_server2
    restart: always
    command: agent -server -client=0.0.0.0 -retry-join=consul_server1 -node=consul_server2 -config-dir=/home/acl.json
    volumes:
      - /data/docker_file/consul_cluster/acl-server.json:/home/acl.json
  consul_server3:
    image: consul:latest
    container_name: consul_server3
    restart: always
    command: agent -server -client=0.0.0.0 -retry-join=consul_server1 -node=consul_server3 -config-dir=/home/acl.json
    volumes:
      - /data/docker_file/consul_cluster/acl-server.json:/home/acl.json
  consul_client:
    image: consul:latest
    container_name: consul_client
    restart: always
    ports:
      - 8500:8500
    command: agent -client=0.0.0.0 -retry-join=consul_server1 -ui -node=consul_client -config-dir=/home/acl_client.json
    volumes:
      - /data/docker_file/consul_cluster/acl-client.json:/home/acl_client.json
```

### 八、安装zookeeper

1. docker-compose方式安装单机版zookeeper

```yml
version: "3"
services:
  zookeeper1:
    image: zookeeper:3.4.11
    restart: always
    hostname: zookeeper1
    container_name: zookeeper_1
    ports:
      - 2181:2181
    volumes:
      - /data/docker_file/zookeeper/data:/data
      - /data/docker_file/zookeeper/datalog:/datalog
```

2. docker-compose方式安装集群版zookeeper

```yml
version: "3"
services:
  zookeeper1:
    image: zookeeper:3.4.11
    restart: always
    hostname: zookeeper1
    container_name: zookeeper_1
    ports:
      - 2181:2181
    volumes:
      - /data/docker_file/zookeeper_cluster/zookeeper1/data:/data
      - /data/docker_file/zookeeper_cluster/zookeeper1/datalog:/datalog
    environment:
      ZOO_MY_ID: 1
      ZOO_SERVERS: server.1=zookeeper1:2888:3888 server.2=zookeeper2:2888:3888 server.3=zookeeper3:2888:3888
  zookeeper2:
    image: zookeeper:3.4.11
    restart: always
    hostname: zookeeper2
    container_name: zookeeper_2
    ports:
      - 2182:2181
    volumes:
      - /data/docker_file/zookeeper_cluster/zookeeper2/data:/data
      - /data/docker_file/zookeeper_cluster/zookeeper2/datalog:/datalog
    environment:
      ZOO_MY_ID: 2
      ZOO_SERVERS: server.1=zookeeper1:2888:3888 server.2=zookeeper2:2888:3888 server.3=zookeeper3:2888:3888
  zookeeper3:
    image: zookeeper:3.4.11
    restart: always
    hostname: zookeeper3
    container_name: zookeeper_3
    ports:
      - 2183:2181
    volumes:
      - /data/docker_file/zookeeper_cluster/zookeeper3/data:/data
      - /data/docker_file/zookeeper_cluster/zookeeper3/datalog:/datalog
    environment:
      ZOO_MY_ID: 3
      ZOO_SERVERS: server.1=zookeeper1:2888:3888 server.2=zookeeper2:2888:3888 server.3=zookeeper3:2888:3888
```

