#!/bin/bash

# 设置提交信息
set message=$1


if [ -z $message ] ; then 
	echo "Please Input Git Commit Message:"
	read message
fi

if [ -z $message ] ; then 
	message="update docker compose"
fi

# 执行git提交
git add .
git commit -m "$message" 
git push 

echo commit and push success !


