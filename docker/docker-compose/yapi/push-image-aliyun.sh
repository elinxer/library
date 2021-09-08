#!/bin/sh

image_name=$1
image_version=$2

docker login -u kancy1994 registry.cn-hangzhou.aliyuncs.com

if docker images | grep "registry.cn-hangzhou.aliyuncs.com/kancy/${image_name}" | grep "${image_version}" &> /dev/null
then
  ehco "tag finished!"
else
  docker tag ${image_name}:${image_version} registry.cn-hangzhou.aliyuncs.com/kancy/${image_name}:${image_version}
  ehco "tag finished!"
fi

docker push registry.cn-hangzhou.aliyuncs.com/kancy/${image_name}:${image_version}
echo "push finished!"