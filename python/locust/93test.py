# register 压测脚本
# 使用说明：
#   可以直接运行脚本
#   可以命令 locust -f 93test.py --host=http://test-api.xxxx.com
#   访问：http://localhost:8089/
#
# coding=utf-8
import random
from locust import HttpLocust, TaskSet, task


class GameSdkApi(TaskSet):
    # 账号注册
    @task(1)
    def post_register(self):
        # 定义请求头
        header = {
            "User-Agent": "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36"
        }

        username = random.randint(19888888, 110088888)
        username = 'testd' + username.__str__()
        data = {
            "username": username,
            "password": "123456",
            "referer": "channel1",
            "device_model": "MI 8 Lite",
            "ad_param": "",
            "referer_param": "",
            "reg_type": "2",
            "device_os": "Android 8.1.0",
            "platform": "android",
            "device_imei": "869041043679913",
            "device_brand": "Xiaomi",
            "sdk_version": "1.0",
            "device_network": "wifi",
            "game_id": "10",
        }
        # req = self.client.post("/user/register", data=data, headers=header, verify=False)
        # if req.status_code == 200:
        #     print("success")
        # else:
        #     print("fails")
        req = self.client.post("/user/register", data=data, headers=header, verify=False, catch_response=True)
        print("%s => %s" % (req.json().get('code'), req.json().get('msg')))
        assert req.json().get('code') == 1


class WebSitUser(HttpLocust):
    task_set = GameSdkApi
    min_wait = 3000  # 单位为毫秒
    max_wait = 6000  # 单位为毫秒


if __name__ == "__main__":
    import os

    # http://localhost:8089
    os.system("locust -f 93test.py --host=http://test-api.xxxxx.com")
