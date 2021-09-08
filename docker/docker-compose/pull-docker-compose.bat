@echo off

git pull

echo pull success !
echo auto close window in 3 seconds !

ping -n 3 127.0.0.1>nul

@echo on