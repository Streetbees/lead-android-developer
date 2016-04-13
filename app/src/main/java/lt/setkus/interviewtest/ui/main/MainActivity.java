package lt.setkus.interviewtest.ui.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import lt.setkus.interviewtest.app.R;
import lt.setkus.interviewtest.configuration.dagger.HasComponent;
import lt.setkus.interviewtest.configuration.dagger.component.ComicsComponent;
import lt.setkus.interviewtest.configuration.dagger.component.DaggerComicsComponent;
import lt.setkus.interviewtest.configuration.dagger.module.ComicsModule;
import lt.setkus.interviewtest.ui.BaseActivity;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public class MainActivity extends BaseActivity implements HasComponent<ComicsComponent> {

    private ComicsComponent comicsComponent;

    private void initComicsComponent() {
        comicsComponent = DaggerComicsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .comicsModule(new ComicsModule())
                .build();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null == savedInstanceState) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.container, ComicsFragment.newInstance(new Bundle()), "comics_fragment")
                    .commit();

            initComicsComponent();
        }
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public ComicsComponent getComponent() {
        return comicsComponent;
    }
}
