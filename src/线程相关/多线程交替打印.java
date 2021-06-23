package 线程相关;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author huahan
 * date: 2021/6/19.
 * version:1.0
 */
public class 多线程交替打印 {
    static Thread ta,tb,tc;
    static int state = 0;
    static Lock lock = new ReentrantLock();
    public static void main(String[] args) {
        ta = new Thread(()->{
            while(state < 10) {
                try {
                    lock.lock();
                    while(state % 3 == 0) {
                        System.out.println(Thread.currentThread().getName()+"    A");
                        state++;
                    }
                }finally {
                    lock.unlock();
                }
            }
        },"thread-a");

        tb = new Thread(()->{
            while(state < 10) {
                try {
                    lock.lock();
                    while (state % 3 == 1) {
                        System.out.println(Thread.currentThread().getName() + "    B");
                        state++;
                    }
                } finally {
                    lock.unlock();
                }
            }
        },"thread-b");

        tc = new Thread(()->{
            while(state < 10) {
                try {
                    lock.lock();
                    while (state % 3 == 2) {
                        System.out.println(Thread.currentThread().getName() + "    C");
                        state++;
                    }
                } finally {
                    lock.unlock();
                }
            }
        },"thread-c");

        ta.start();
        tb.start();
        tc.start();
    }
}
