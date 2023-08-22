package com.example.webflux.test;

public class TestThreadLocal {

    // 可以通过 InheritableThreadLocal 来协作主线程和子线程直接的通信
    public static ThreadLocal<String> threadLocal = new ThreadLocal<String>();
    public static InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("hello world");
        inheritableThreadLocal.set("hello world InheritableThreadLocal");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // (4) 子线程输出线程变量的值
                System.out.println("thread: " + threadLocal.get());
                System.out.println("child thread: " + inheritableThreadLocal.get());
            }
        });

        thread.start();

        System.out.println("main: " + threadLocal.get());
        System.out.println("main thread: " + inheritableThreadLocal.get());
    }
}