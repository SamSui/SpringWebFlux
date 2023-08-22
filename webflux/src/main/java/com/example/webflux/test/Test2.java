package com.example.webflux.test;

public class Test2 {

    // 可以通过volatile关键字来保证主线程和子线程的可见性
    // 实际上是 JIT 优化导致的问题
    public static boolean stop = false;
    public static void main(String[] args) {
        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            stop = true;
            System.out.println("子线程结束");
        }).start();

        go();

    }

    public static void go()  {
        while (!stop){
//            try {
//                Thread.sleep(2000);
//            }catch (Exception e){
//
//            }

            System.out.println("wewe: " + stop);

        }
        System.out.println("主线成stop");
    }
}
