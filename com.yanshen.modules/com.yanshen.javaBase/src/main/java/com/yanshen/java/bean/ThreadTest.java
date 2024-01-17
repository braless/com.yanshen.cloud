package com.yanshen.java.bean;

/**
 * 线程测试
 */
public class ThreadTest {
    static  ThreadLocal<Integer> threadLocal=new ThreadLocal();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 =new Thread(()->{
            System.out.println(threadLocal.get());
            threadLocal.set(0);
            System.out.println(threadLocal.get());
            System.out.println("T1的---->"+Thread.currentThread().getName());
        });
        Thread t2 =new Thread(()->{
            System.out.println(threadLocal.get());
            threadLocal.set(1);
            System.out.println("T2的---->"+Thread.currentThread().getName());
            System.out.println(threadLocal.get());
        });
        t1.start();
        System.out.println("t1的---->"+t1.getName());
        t1.join();
        t2.start();
        System.out.println("t2的---->"+t2.getName());
        threadLocal.remove();

    }
}
