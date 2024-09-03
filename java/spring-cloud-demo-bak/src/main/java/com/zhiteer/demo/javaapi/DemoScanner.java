package com.zhiteer.demo.javaapi;

import java.util.Scanner;

/**
 * 扫描类包 - API
 *
 * 可以解析基本类型和字符串的简单文本扫描器
 */
public class DemoScanner {


    public static void main(String[] args) {

        Scanner myScanner = new Scanner(System.in);

        System.out.println("请输入一个数字");

        int sc = myScanner.nextInt();
        System.out.println(sc);


    }


}
