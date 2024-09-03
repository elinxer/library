package com.zhiteer.demo.javaapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期demo ，采用util包
 */
public class DemoDate {


    public static void DemoDate1() {

        System.out.println(new Date());

        Date date = new Date();
        System.out.println(date.toString());

        System.out.println(date.getTime());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        System.out.println(format.format(date));

    }


    public static void main(String[] args) {


        DemoDate.DemoDate1();


        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");

        String input = args.length == 0 ? "1818-11-11" : args[0];

        System.out.print(input + " Parses as ");

        Date t;

        try {

            t = ft.parse(input);
            System.out.println(t);

            SimpleDateFormat ft2 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
            System.out.println(ft2.format(t));



        }
        catch (Exception e) {
            System.out.println("Unparseable using " + ft);
        }

    }



}
