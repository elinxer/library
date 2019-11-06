### python3 压测框架使用demo

[TOC]

- github ```https://github.com/locustio/locust```


http://www.testclass.net/locust/help

#### 启动命令

启动参数：

--no-web 表示不使用Web界面运行测试。

-c 设置虚拟用户数。

-r 设置每秒启动虚拟用户数。

-t 设置设置运行时间

```
locust -f load_test.py --host=https://www.baidu.com --no-web -c 10 -r 2 -t 1m
```
