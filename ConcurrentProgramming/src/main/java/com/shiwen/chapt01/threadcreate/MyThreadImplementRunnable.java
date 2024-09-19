package com.shiwen.chapt01.threadcreate;

public class MyThreadImplementRunnable implements Runnable{

    public static void main(String[] args) {
        MyThreadImplementRunnable myThreadImplementRunnable = new MyThreadImplementRunnable();
        Thread thread = new Thread(myThreadImplementRunnable);
        thread.setName("MyThreadImplementRunnable");
        thread.start();
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
