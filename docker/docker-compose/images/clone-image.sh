#!/bin/sh

SRC_IMAGE=
TARGET_IMAGE=

docker login -u kancy1994 -p kancy********* registry.cn-hangzhou.aliyuncs.com

docker pull ${SRC_IMAGE}
docker tag ${SRC_IMAGE} ${TARGET_IMAGE}
docker push ${TARGET_IMAGE}
