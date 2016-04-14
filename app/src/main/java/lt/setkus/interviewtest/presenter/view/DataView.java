package lt.setkus.interviewtest.presenter.view;

import android.content.Context;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public interface DataView {
    void showLoading();
    void hideLoading();
    void showError(Throwable throwable);
    Context getContext();
}
