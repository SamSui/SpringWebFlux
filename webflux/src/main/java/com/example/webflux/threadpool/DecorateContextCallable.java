package com.example.webflux.threadpool;

import java.util.concurrent.Callable;

public class DecorateContextCallable<V> extends DecorateContext implements Callable<V> {
    private final Callable<V> callable;

    private DecorateContextCallable(Callable<V> task){
        this.callable = task;
    }

    public static <V> Callable<V> create(Callable<V> task){
        return new DecorateContextCallable(task);
    }
    @Override
    public V call() throws Exception {
        try {
            super.decorate();
            return callable.call();
        }finally {
            super.recoverAndClear();
        }
    }
}
