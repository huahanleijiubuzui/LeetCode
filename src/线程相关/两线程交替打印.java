package 线程相关;

import java.util.concurrent.locks.LockSupport;

/**
 * @author huahan
 * date: 2021/6/19.
 * version:1.0
 */
public class 两线程交替打印 {
    private static volatile int num = 0;
    static Thread t2,t1;

    private static void printNum() {
        System.out.println(Thread.currentThread().getName() + "  "+ num);
        num++;
    }


    public static void main(String[] args) throws InterruptedException {
        t1 = new Thread(()->{
            while(num < 10) {
                printNum();
                LockSupport.unpark(t2);
                LockSupport.park(t1);
            }
        },"t1 ");

        String name = Thread.currentThread().getName();
        t2 = new Thread(()->{
            while(num < 10) {
                LockSupport.park(t2);
                printNum();
                LockSupport.unpark(t1);
            }
        },"t2 ");
        t1.start();
        t2.start();
        t2.join();
        System.out.println("name-------打印完了-----");
    }
}
