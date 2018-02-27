package cc.litstar.pool;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TimerManager {
	private static int POOL_SIZE = 8;
	protected ScheduledExecutorService executor;
	private ConcurrentHashMap<Integer, ScheduledFuture> scheduledFutureMap;
	private volatile AtomicInteger timerID;
	
	private static TimerManager timerManager = null;
	
	private TimerManager() {
		;
	}

	public static TimerManager getInstance() {
		if(timerManager != null) {
			timerManager = new TimerManager();
			timerManager.init();
		}
		return timerManager;
	}

	public static TimerManager newInstance() {
		timerManager = new TimerManager();
		timerManager.init();
		return timerManager;
	}
	
    public void init() {
        final ThreadGroup tg = new ThreadGroup("Scheduled Task Threads");
        ThreadFactory f = new ThreadFactory() {
            AtomicInteger id = new AtomicInteger();

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(tg, runnable, "Scheduled-" + id.getAndIncrement());
            }
        };
        executor = Executors.newScheduledThreadPool(POOL_SIZE, f);
        scheduledFutureMap = new ConcurrentHashMap<>();
        timerID = new AtomicInteger(0);
    }
	
	public ScheduledExecutorService getScheduledExecutor() {
		return executor;
	}
	
    public int addTimerTask(TimeoutListener listener, int timeout, TimeUnit timeUnit) {
        int curTimerID = timerID.getAndIncrement();
        ScheduledFuture<?> future = executor.schedule(() -> listener.onTimeout(curTimerID), timeout, timeUnit);
        scheduledFutureMap.put(curTimerID, future);
        return curTimerID;
    }
    
    public int addRepeatingTimerTask(TimeoutListener listener, int delay, int interval, TimeUnit timeUnit) {
        int curTimerID = timerID.getAndIncrement();
        ScheduledFuture<?> future = executor.scheduleWithFixedDelay(() -> listener.onTimeout(curTimerID), delay, interval, timeUnit);
        scheduledFutureMap.put(curTimerID, future);
        return curTimerID;
    }
    
    public void cancelTimer(int timerID) {
        ScheduledFuture<?> future = scheduledFutureMap.get(timerID);
        if (future != null) 
        {
            scheduledFutureMap.remove(timerID);
            future.cancel(true);
        }
    }
}