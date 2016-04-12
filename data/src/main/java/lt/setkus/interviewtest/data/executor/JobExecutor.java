package lt.setkus.interviewtest.data.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import lt.setkus.interviewtest.domain.executor.ThreadExecutor;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
@Singleton
public class JobExecutor implements ThreadExecutor {

    private static final int INITIAL_POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 5;

    // Amount of time an idle thread waits before terminating
    private static final long KEEP_ALIVE_TIME = 10;

    // The time unit of seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private final BlockingQueue<Runnable> workQueue;
    private final ThreadPoolExecutor threadPoolExecutor;
    private final ThreadFactory threadFactory;

    @Inject
    public JobExecutor() {
        workQueue = new LinkedBlockingQueue<Runnable>();
        threadFactory = new JobThreadFactory();

        threadPoolExecutor = new ThreadPoolExecutor(
                INITIAL_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                workQueue,
                threadFactory
        );
    }

    @Override
    public void execute(Runnable command) {
        if (null == command) {
            throw new IllegalArgumentException("Runnable to execute can not be null");
        }

        threadPoolExecutor.execute(command);
    }

    private static class JobThreadFactory implements ThreadFactory {

        private static final String THREAD_NAME = "interview_test_";

        private int counter = 0;

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, THREAD_NAME + counter);
        }
    }
}
