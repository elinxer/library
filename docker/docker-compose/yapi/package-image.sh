#!/bin/sh

image_name=yapi
# 版本1.8.5,1.9.2
image_version=1.9.2

#sudo docker rmi ${image_name}:${image_version}
if docker images | grep "${image_name}" | grep "${image_version}" &> /dev/null
then
    echo finished!
    exit
fi
wget https://github.com/YMFE/yapi/archive/v${image_version}.tar.gz && mv v${image_version}.tar.gz yapi.tar.gz
docker build -t ${image_name}:${image_version} .
docker images
rm -rf yapi.tar.gz
echo "package docker image ${image_name}:${image_version} success!"