package com.shiwen.chapt01.threadcreate;

public class MyThreadInternalRunnable {

    public static void main(String[] args) {
        Thread myThreadInternalRunnable = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        });
        myThreadInternalRunnable.setName("MyThreadInternalRunnable");
        myThreadInternalRunnable.start();
    }
}
