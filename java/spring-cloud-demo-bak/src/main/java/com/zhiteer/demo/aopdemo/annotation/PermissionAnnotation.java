package com.zhiteer.demo.aopdemo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.*;

/**
 * 自定义注解 demo
 *
 *  ===  @Target、@Retention、@Documented 注解简介
 *  https://blog.csdn.net/mu_wind/article/details/102755071
 *
 * == @interface 意思是声明一个注解，方法名对应参数名，返回值类型对应参数类型。
 *
 *
 * == @Target注解用于定义注解的使用位置，如果没有该项，表示注解可以用于任何地方
 * == @Target的ElementType取值有以下类型：
 * TYPE：类，接口或者枚举
 * FIELD：域，包含枚举常量
 * METHOD：方法
 * PARAMETER：参数
 * CONSTRUCTOR：构造方法
 * LOCAL_VARIABLE：局部变量
 * ANNOTATION_TYPE：注解类型
 * PACKAGE：包
 *
 *
 * == @Retention注解用于指明修饰的注解的生存周期，即会保留到哪个阶段
 * RetentionPolicy的取值包含以下三种：
 *
 * SOURCE：源码级别保留，编译后即丢弃。
 * CLASS：编译级别保留，编译后的class文件中存在，在jvm运行时丢弃，这是默认值。
 * RUNTIME：运行级别保留，编译后的class文件中存在，在jvm运行时保留，可以被反射调用。
 *
 *
 * == @Documented
 * 指明修饰的注解，可以被例如javadoc此类的工具文档化，只负责标记，没有成员取值。
 *
 *
 * == @Inherited
 * == @Inherited注解用于标注一个父类的注解是否可以被子类继承，如果一个注解需要被其子类所继承，则在声明时直接使用@Inherited注解即可。如果没有写此注解，则无法被子类继承
 *
 * @author Elinx<yangdongsheng03>
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionAnnotation {



}
