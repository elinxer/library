package com.zhiteer.demo.javaapi;


/**
 * Demo lambda
 */
public class DemoLambda {

    static DemoLambda tester = new DemoLambda();

    public static void DemoThread() {
        new Thread(() -> System.out.println("Hello World 1111")).start();

        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();

        System.out.println("Hello World 3333");
    }

    interface MathOperation {
        int operation(int a, int b);
    }

    interface GreetingService {
        void sayMessage(String message);
    }


    private int operate(int a, int b, MathOperation mathOperation){
        return mathOperation.operation(a, b);
    }

    public static void main(String[] args) {

        // 类型声明
        MathOperation addition = (int a, int b) -> a + b + 2;

        System.out.println(tester.operate(1,2, addition));
        System.out.println(tester.operate(2,3, addition));

        System.out.println("lambda thread demo ======= \n");

        DemoLambda.DemoThread();


    }



}
