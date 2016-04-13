package lt.setkus.interviewtest.presenter;

import lt.setkus.interviewtest.presenter.view.DataView;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public interface Presenter<T extends DataView> {
    void setView(T dataView);
    void onResume();
    void onPause();
    void onDestroy();
}
