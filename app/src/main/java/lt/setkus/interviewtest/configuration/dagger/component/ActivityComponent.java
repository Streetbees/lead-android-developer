package lt.setkus.interviewtest.configuration.dagger.component;

import android.app.Activity;

import dagger.Component;
import lt.setkus.interviewtest.configuration.dagger.PerActivity;
import lt.setkus.interviewtest.configuration.dagger.module.ActivityModule;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();
}
