package com.silang.javalib.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TestThread {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable able = new MyCallable();
        FutureTask<String> future = new FutureTask<String>(able);
        new Thread(future).start();
        System.out.println(future.get());//阻塞的

        Mythread thread = new Mythread();
        thread.start();
        Thread.sleep(2000);
        thread
    }


    static class MyCallable implements Callable<String>{
        @Override
        public String call() throws Exception {
            return "zhonglisilang";
        }
    }

    static  class  Mythread extends Thread{
        @Override
        public void run() {
            super.run();
            while(!isInterrupted()){//TODO 判断是不是处于要求中断状态
                System.out.println("dosth");
            }
        }
    }
}
