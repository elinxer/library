#!/bin/sh
#export LANG=zh_CN
# ❤★☆●▲
# sh restart.sh /opt/apps order-service 8080 test

if [ $# -lt 2 ] ; then
    echo "USAGE: $0 <HOME_PATH> <SERVICE_NAME> <PORT> [PROFILE:test]"
    echo " e.g.: $0 /opt/apps order-service 8080 test"
    exit 1;
fi

HOME_PATH=$1
SERVICE_NAME=$2
SERVICE_PORT=$3
SERVICE_PROFILE=$4

if [ "$HOME_PATH"=="." ] ; then
    $HOME_PATH=$PWD
fi
if [ "$HOME_PATH"=="./" ] ; then
    $HOME_PATH=$PWD
fi

if [ ! $SERVICE_PORT ] ; then
    SERVICE_PORT=0
fi

if [ ! $SERVICE_PROFILE ] ; then
    SERVICE_PROFILE=test
fi

RLOG_PATH=${HOME_PATH}/logs/restart_${SERVICE_NAME}.log
DATA_PATH=${HOME_PATH}/${SERVICE_NAME}.jar

#ready

echo -e "\033[43;37m 准备重启 $SERVICE_NAME 服务 \033[0m"
echo '-----------------------------------------------------------'
echo -e "\033[34m step1    ready \033[0m"
source /etc/profile
mkdir -p $HOME_PATH/logs/
cd "$HOME_PATH"
RUN=$JAVA_HOME/bin/java

#stop
echo -e "\033[34m step2    stop $SERVICE_NAME service \033[0m"
if [ $(pgrep -f $DATA_PATH | wc -l) -ge 1 ]
then
    pgrep -f $DATA_PATH | xargs kill -9
    echo -e "\033[31m          kill $SERVICE_NAME service successed \033[0m"
fi

#start
echo -e "\033[34m step3    restart $SERVICE_NAME service \033[0m"
nohup $RUN -Xms512m -Xmx512m -Xmn256m -server -jar ${DATA_PATH} --server.port=${SERVICE_PORT} --spring.profiles.active=${SERVICE_PROFILE} > ${RLOG_PATH} 2>&1 &

#result
echo '-----------------------------------------------------------'
echo '正在启动，请稍等...'
sleep 10
tail -n 50 $RLOG_PATH
echo ''
echo -e "\033[35m ❤ $(jps -ml | grep $DATA_PATH) \033[0m"
echo ''
echo -e "\033[35m ❤ run script to show listen port: netstat -atnlp | grep java \033[0m"
echo ''
echo "服务启动成功！"
echo ''
