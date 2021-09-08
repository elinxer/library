#!/bin/sh

# https://blog.csdn.net/mtldswz312/article/details/99948905
# Q:Error response from daemon: Get https://192.168.3.104/v2/: dial tcp 192.168.3.104:443: connect: connection refused
# A:vi /etc/docker/daemon.json
# {"registry-mirrors": ["http://ced808ab.m.daocloud.io"],"insecure-registries": ["192.168.3.104"]}

cd /usr/local/
wget https://storage.googleapis.com/harbor-releases/release-1.8.0/harbor-offline-installer-v1.8.0.tgz
tar zxvf harbor-offline-installer-v1.8.0.tgz
rm -rf harbor-offline-installer-v1.8.0.tgz
cd harbor
cat harbor.yml
# 需要手动设置IP地址或者域名
sed -i 's/hostname: reg.mydomain.com/hostname: 192.168.3.104/g' harbor.yml
sh prepare
sh install.sh

# http://192.168.3.104/harbor
# 登录：docker login -u kancy -p Harbor12345 192.168.3.104
# 镜像：docker tag kancy/spring-boot-docker 192.168.3.104/devs/kancy/spring-boot-docker:1.0
# 推送：docker push 192.168.3.104/devs/kancy/spring-boot-docker