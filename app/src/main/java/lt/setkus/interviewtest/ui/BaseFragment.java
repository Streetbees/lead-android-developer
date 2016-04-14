package lt.setkus.interviewtest.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import lt.setkus.interviewtest.configuration.dagger.HasComponent;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public class BaseFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public <C> C getComponent(Class<C> component) {
        return component.cast(((HasComponent<C>) getActivity()).getComponent());
    }
}
