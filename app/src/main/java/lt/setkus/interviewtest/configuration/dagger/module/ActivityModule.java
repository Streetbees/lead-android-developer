package lt.setkus.interviewtest.configuration.dagger.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import lt.setkus.interviewtest.configuration.dagger.PerActivity;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 * @since 2016.02.18
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public Activity getActivity() {
        return this.activity;
    }
}
