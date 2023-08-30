package com.example.webflux.threadpool;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class DecorateContextThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    @Override
    public void execute(Runnable task) {
        super.execute(DecorateContextRunnable.create(task));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(DecorateContextRunnable.create(task));
    }

    @Override
    public <T> CompletableFuture<T> submitCompletable(Callable<T> task) {
        return super.submitCompletable(DecorateContextCallable.create(task));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(DecorateContextCallable.create(task));
    }
}
