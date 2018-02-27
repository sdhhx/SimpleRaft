package cc.litstar.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
    private static ThreadPoolManager threadPoolManager;
    private ThreadPoolManager(){};
    
    public synchronized static ThreadPoolManager getInstance() {
        if(threadPoolManager == null) {
            threadPoolManager = new ThreadPoolManager();
        }
        return threadPoolManager;
    }

    private ThreadPoolExecutor executor;

    public void execute(Runnable r){
        if(executor == null) {
            /**
             int corePoolSize,核心线程的数量，在正常情况下，线程池中同时运行的线程的数量
             int maximumPoolSize,最大线程的数量，在非正常的情况下（等待区域满了的情况下），线程池中同时运行的线程的数量
             long keepAliveTime,空闲时间  5
             TimeUnit unit,空闲时间的单位
             BlockingQueue<Runnable> workQueue,等待区域
             ThreadFactory threadFactory,线程创建的工厂
             RejectedExecutionHandler handler 异常处理机制
             */
            executor = new ThreadPoolExecutor(
                    4, 8, 0,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(20),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy());
            int cpuCount = Runtime.getRuntime().availableProcessors();
            int corePoolSize = cpuCount * 2 + 1;
        }
        executor.execute(r);
    }

    public void cancel(Runnable runnable) {
        if(executor != null) {
            executor.getQueue().remove(runnable);
        }
    }
}
