package lt.setkus.interviewtest;

import javax.inject.Inject;
import javax.inject.Singleton;

import lt.setkus.interviewtest.domain.executor.PostExecutionThread;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 * @since 2016.02.18
 */
@Singleton
public class UIThread implements PostExecutionThread {

    @Inject
    public UIThread() {}

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
