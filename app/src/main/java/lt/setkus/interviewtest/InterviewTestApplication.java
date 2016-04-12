package lt.setkus.interviewtest;

import android.app.Application;

import lt.setkus.interviewtest.configuration.dagger.component.ApplicationComponent;
import lt.setkus.interviewtest.configuration.dagger.component.DaggerApplicationComponent;
import lt.setkus.interviewtest.configuration.dagger.module.ApplicationModule;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public class InterviewTestApplication extends Application {
    private ApplicationComponent applicationComponent;

    private void init() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        init();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
