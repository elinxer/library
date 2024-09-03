### 说明


这里是包的根目录，所有项目入口都由此开始


### 如何让nacos发现本包

如果本包要注册到nacos上，如何操作呢？


在主入口处的DemoApplication类加上注解：

```
...

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.zhiteer.demo")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

...

```

如此可以直接注册到nacos上


注意，不同的项目注册到nacos上要注意启动的端口以及路由不能重复，尤其在一个项目里面测试的，容易产生奇怪的问题。

同时，这里是使用 openfeign 来作为消费者端，务必引入该依赖。


```

 <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>

```

