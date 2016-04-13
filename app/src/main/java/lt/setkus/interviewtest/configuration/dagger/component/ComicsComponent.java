package lt.setkus.interviewtest.configuration.dagger.component;

import dagger.Component;
import lt.setkus.interviewtest.configuration.dagger.PerActivity;
import lt.setkus.interviewtest.configuration.dagger.module.ActivityModule;
import lt.setkus.interviewtest.configuration.dagger.module.ComicsModule;
import lt.setkus.interviewtest.ui.main.ComicsFragment;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ComicsModule.class})
public interface ComicsComponent {
    void inject(ComicsFragment comicsFragment);
}
