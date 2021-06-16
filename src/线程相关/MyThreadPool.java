package 线程相关;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : huahan
 * @date : 2021/6/10
 */
public class MyThreadPool {
    public static void main(String[] args) {
       ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 8, 6, TimeUnit.SECONDS,
               new LinkedBlockingDeque<>());
       // 往线程池中循环添加线程
        for(int i = 1; i<= 20; i++) {
            // 创建线程对象
            MyThread myTask = new MyThread(i);
            // 开启线程
            executor.execute(myTask);
            // 获取线程池中对应的参数
            System.out.println("线程池中线程数目：" +executor.getPoolSize() + "，队列中等待执行的任务数目："+
                    executor.getQueue().size() + "，已执行完的任务数目："+executor.getCompletedTaskCount() +
                    "任务总数量" + executor.getTaskCount() + " 活跃数量"+ executor.getActiveCount());

            /*if(i == 18) {
                List<Runnable> runnables = executor.shutdownNow();
                System.out.println("-----------------已终止的线程详情-------------------");
                for(Runnable runnable : runnables) {
                    MyThread myThread = (MyThread) runnable;
                    System.out.println("已终止线程："+ myThread.toString());
                }
            }*/
        }

       /* try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        System.out.println("===================开始执行第二批线程========================");

        for(int i = 1; i<= 5; i++) {
            // 创建线程对象
            MyThread myTask = new MyThread(i);
            // 开启线程
            executor.execute(myTask);
            // 获取线程池中对应的参数
            System.out.println("batch2--线程池中线程数目：" +executor.getPoolSize() + "，队列中等待执行的任务数目："+
                    executor.getQueue().size() + "，已执行完的任务数目："+executor.getCompletedTaskCount() +
                    "任务总数量" + executor.getTaskCount() + " 活跃数量"+ executor.getActiveCount());
        }

        // 关闭线程池
        System.out.println("-----关闭线程池");
        executor.shutdown();
        System.out.println("batch3--线程池中线程数目：" +executor.getPoolSize() + "，队列中等待执行的任务数目："+
                executor.getQueue().size() + "，已执行完的任务数目："+executor.getCompletedTaskCount() +
                "任务总数量" + executor.getTaskCount() + " 活跃数量"+ executor.getActiveCount());    }
}
