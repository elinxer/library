#!/bin/sh
image_name=sentinel-dashboard
image_version=1.6.3

#sudo docker rmi ${image_name}:${image_version}
if docker images | grep "${image_name}" | grep "${image_version}" &> /dev/null
then
    echo finished!
    exit
fi

sudo wget https://github.com/alibaba/Sentinel/releases/download/${image_version}/sentinel-dashboard-${image_version}.jar
sudo docker build -t ${image_name}:${image_version} ./
sudo rm -rf sentinel-dashboard-${image_version}.jar
echo finished!