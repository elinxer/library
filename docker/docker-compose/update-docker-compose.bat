@echo off

rem 设置提交信息
set /p message=please input commit message:
if not defined message (
    set message=update docker-compose
)

rem 执行git提交
git add .
git commit -m "%message%"
git push

echo commit and push success !
echo auto close window in 3 seconds !

ping -n 3 127.0.0.1>nul

@echo on