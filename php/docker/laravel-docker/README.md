### 说明

该yml仅仅针对 ``` laravel5.* ``` 以上开发环境

且环境基于docke

### 使用说明

在电脑上新建一个目录/或现有目录都可，复制docker-compose.yml文件到目录下，如有配置修改可以查看下面的说明。
- 运行docker命令

```
docker-compose up -d
```

- 备注：亲测在win10 docker最新版下有效，且一直使用

```
version: '2.1' # 版本 > 1
services: 
    laravel_docker: # 名称
      image: laraedit/laraedit  # 采用第三方完整环境包
      ports: 
        - "8005:80" # 端口
      volumes:
        - .:/var/www/html/app # 挂载当前目录到容器上

```