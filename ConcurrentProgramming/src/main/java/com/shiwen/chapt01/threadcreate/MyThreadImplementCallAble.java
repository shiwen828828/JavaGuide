package com.shiwen.chapt01.threadcreate;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MyThreadImplementCallAble implements Callable {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyThreadImplementCallAble myThreadExtendThread = new MyThreadImplementCallAble();
        FutureTask stringFutureTask = new FutureTask(myThreadExtendThread);
        Thread thread = new Thread(stringFutureTask);
        thread.setName("MyThreadExtendThread");
        thread.start();
        System.out.println("main method execute");
        Object o = stringFutureTask.get();
        System.out.println(o.toString());
    }

    @Override
    public Object call() throws Exception {
        System.out.println("call method start");
        return Thread.currentThread().getName();
    }
}
