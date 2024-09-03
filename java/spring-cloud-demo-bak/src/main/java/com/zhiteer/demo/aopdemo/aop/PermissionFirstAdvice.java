package com.zhiteer.demo.aopdemo.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 *
 * 有人会问，如果我一个接口想设置多个切面类进行校验怎么办？这些切面的执行顺序如何管理？
 * 很简单，一个自定义的AOP注解可以对应多个切面类，这些切面类执行顺序由@Order注解管理，该注解后的数字越小，所在切面类越先执行。
 *
 * == @Order(0)
 * == @Order(1)
 * == @Order(2)
 *
 * @author Elinx<yangdongsheng03>
 */
@Aspect
@Component
public class PermissionFirstAdvice {

    // 定义一个切面，括号内写入第1步中自定义注解的路径
    @Pointcut("@annotation(com.zhiteer.demo.aopdemo.annotation.PermissionAnnotation)")
    private void permissionCheck() {
    }

    // 实现上面定义的切面
    @Around("permissionCheck()")
    public Object permissionCheckFirst(ProceedingJoinPoint joinPoint) throws Throwable {

        System.out.println("===================第一个切面===================：" + System.currentTimeMillis());

        //获取请求参数，详见接口类
        Object[] objects = joinPoint.getArgs();
        Long id = ((JSONObject) objects[0]).getLong("id");
        String name = ((JSONObject) objects[0]).getString("name");
        System.out.println("id1->>>>>>>>>>>>>>>>>>>>>>" + id);
        System.out.println("name1->>>>>>>>>>>>>>>>>>>>>>" + name);

        // id小于0则抛出非法id的异常
        if (id < 0) {
            return JSON.parseObject("{\"message\":\"illegal id\",\"code\":403}");
        }

        return joinPoint.proceed();
    }


}
