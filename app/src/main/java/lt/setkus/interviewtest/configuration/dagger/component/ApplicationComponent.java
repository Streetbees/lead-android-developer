package lt.setkus.interviewtest.configuration.dagger.component;

import javax.inject.Singleton;

import dagger.Component;
import lt.setkus.interviewtest.configuration.dagger.module.ApplicationModule;
import lt.setkus.interviewtest.domain.executor.PostExecutionThread;
import lt.setkus.interviewtest.domain.executor.ThreadExecutor;
import lt.setkus.interviewtest.ui.BaseActivity;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity activity);

    ThreadExecutor getThreadExecutor();
    PostExecutionThread getPostExecutionThread();
}
