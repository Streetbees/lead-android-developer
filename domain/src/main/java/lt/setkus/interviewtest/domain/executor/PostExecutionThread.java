package lt.setkus.interviewtest.domain.executor;

import rx.Scheduler;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public interface PostExecutionThread {
    Scheduler getScheduler();
}
