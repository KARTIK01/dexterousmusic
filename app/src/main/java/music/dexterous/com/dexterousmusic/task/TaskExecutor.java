package music.dexterous.com.dexterousmusic.task;

import android.os.Process;
import android.support.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import music.dexterous.com.dexterousmusic.utils.logger.PrettyLogger;

/**
 * Created by Kartik on 05/01/16.
 * <p>
 * Helper class to execute tasks on background thread
 */
public class TaskExecutor {

    /**
     * Capacity of the queue holding tasks for the thread pool executor
     */
    private static final int TASK_QUEUE_CAPACITY = 65536;

    /**
     * Time in seconds after which idle worker threads are killed
     */
    private static final int KEEP_ALIVE_WORKER = 20;

    /**
     * Time in seconds after which idle rxjava threads are killed
     */
    private static final int KEEP_ALIVE_RX_JAVA = 5;

    private static TaskExecutor sInstance;

    /**
     * tweak these if it doesn't work well
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * thread pool limits for worker threads
     */
    private static final int CORE_WORKER_POOL_SIZE = CPU_COUNT + 2;
    private static final int MAXIMUM_WORKER_POOL_SIZE = CPU_COUNT * 2 + 1;

    /**
     * thread pool limits for rx java threads
     */
    private static final int CORE_RX_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_RX_POOL_SIZE = CPU_COUNT * 2 + 1;

    /**
     * task queue for worker threads
     */
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingDeque<>(TASK_QUEUE_CAPACITY);

    /**
     * task queue for rx java threads
     */
    private static final BlockingQueue<Runnable> rxJavaWorkQueue = new LinkedBlockingDeque<>(TASK_QUEUE_CAPACITY);

    private static final ThreadFactory sThreadFactory;

    static {
        sThreadFactory = new ThreadFactory() {
            private final AtomicInteger mCount = new AtomicInteger(1);

            public Thread newThread(@NonNull Runnable r) {
                return new BackgroundThread(r, "Background Task # " + mCount.getAndIncrement());
            }
        };
    }

    /**
     * thread pool for executing tasks submitted
     */
    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(CORE_WORKER_POOL_SIZE,
            MAXIMUM_WORKER_POOL_SIZE, KEEP_ALIVE_WORKER, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);

    /**
     * thread pool for creating threads for rx java
     */
    public static final ThreadPoolExecutor RX_THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(CORE_RX_POOL_SIZE,
            MAXIMUM_RX_POOL_SIZE, KEEP_ALIVE_RX_JAVA, TimeUnit.SECONDS, rxJavaWorkQueue, sThreadFactory);

    private TaskExecutor() {
        /**  start core thread right now in order avoid the overhead later on */
        threadPoolExecutor.prestartCoreThread();
    }

    public static TaskExecutor getInstance() {
        if (sInstance == null) {
            synchronized (TaskExecutor.class) {
                if (sInstance == null) {
                    sInstance = new TaskExecutor();
                }
            }
        }
        return sInstance;
    }

    /**
     * executes the given task
     */
    public void executeTask(Runnable task) {
        if (task != null) {
            try {
                threadPoolExecutor.execute(task);
            } catch (Exception e) {
                PrettyLogger.e(e.toString());
                //TODO
//                Crashlytics.logException(e);
            }
        }
    }

    public Future<?> submitTask(Callable<?> task) {
        if (task != null) {
            try {
                return threadPoolExecutor.submit(task);
            } catch (Exception e) {
                PrettyLogger.e(e.toString());
// TODO
//   Crashlytics.logException(e);
            }
        }
        return null;
    }

    static class BackgroundThread extends Thread {

        public BackgroundThread(Runnable runnable, String threadName) {
            super(runnable, threadName);
        }

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            super.run();
        }
    }
}
