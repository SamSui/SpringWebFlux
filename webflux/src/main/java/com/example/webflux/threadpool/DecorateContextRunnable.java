package com.example.webflux.threadpool;

public class DecorateContextRunnable extends DecorateContext implements Runnable {
    private final Runnable runnable;

    public static Runnable create(Runnable task){
        return new DecorateContextRunnable(task);
    }

    private DecorateContextRunnable(Runnable task){
        super();
        this.runnable = task;
    }

    @Override
    public void run() {
        try {
            super.decorate();
            runnable.run();
        }finally {
            super.recoverAndClear();
        }
    }
}
