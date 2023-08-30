package com.example.webflux.test;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,5,9, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3));
        System.out.println("poolSize:" + executor.getPoolSize() + " ,queueSize=" +executor.getQueue().size());
        for (int i = 0; i < 10; i++) {
            final int index = i;
            executor.execute(()->{
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("threadName:" + Thread.currentThread().getName() + " ,index=" +index);
                System.out.println("poolSize:" + executor.getPoolSize() + " ,queueSize=" +executor.getQueue().size());
            });
        }
    }
}
