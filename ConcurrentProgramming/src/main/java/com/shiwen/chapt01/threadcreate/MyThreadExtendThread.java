package com.shiwen.chapt01.threadcreate;

public class MyThreadExtendThread extends Thread{

    public static void main(String[] args) {
        MyThreadExtendThread myThreadExtendThread = new MyThreadExtendThread();
        myThreadExtendThread.setName("MyThreadExtendThread");
        myThreadExtendThread.start();
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
