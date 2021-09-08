#!/bin/sh
# ref: https://cr.console.aliyun.com/repository/cn-hangzhou/devops-t/coredns/details

# login
docker login -u kancy1994 -p kancy********* registry.cn-hangzhou.aliyuncs.com


# pull images
# docker pull registry.cn-hangzhou.aliyuncs.com/devops-t/coredns:[镜像版本号]

# rename
# docker tag registry.cn-hangzhou.aliyuncs.com/devops-t/coredns:[镜像版本号] devops-t/coredns:[镜像版本号]

# tag
# docker tag [ImageId/ImageName] registry.cn-hangzhou.aliyuncs.com/devops-t/coredns:[镜像版本号]

# push
# docker push registry.cn-hangzhou.aliyuncs.com/devops-t/coredns:[镜像版本号]