# springcloud-middleware

### 发送验证码

get
http://127.0.0.1:9101/v1/user-auth/sendSms?phone=13650962253

### 登录获取token

gateway模块以及oauth接口模块都需要开放权限，并且添加路由转换映射

权限配置：
cloud-gateway-server
cloud-auth-server

```
security:
  oauth2:
    enable: true
    ignored: /oauth/**
    token:
      store:
        type: redis
```

路由映射
cloud-gateway.json

```
{
        "id":"cloud-auth",
        "order":8201,
        "predicates":[
            {
                "args":{
                    "pattern":"/api/cloud-auth/**"
                },
                "name":"Path"
            }
        ],
        "uri":"lb://cloud-auth-server",
        "filters":[
            {
                "name":"StripPrefix",
                "args":{
                    "_genkey_0":"2"
                }
            }
        ]
    },
```

http://127.0.0.1:9100/api/cloud-auth/oauth/assist/mobile/token

```post json
{
    "mobile": "13650962253",
    "smsCode": "668929"
}
```

### 验证token访问请求

http://127.0.0.1:9100/api/cloud-library/prompt/search

```
header 关键参数 :
Authorization: Bearer 1c01f992-bb41-4803-a7e1-cbda913a6dc4
client-id: xxxxx
```
