package lt.setkus.interviewtest.ui;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import lt.setkus.interviewtest.InterviewTestApplication;
import lt.setkus.interviewtest.app.R;
import lt.setkus.interviewtest.configuration.dagger.component.ApplicationComponent;
import lt.setkus.interviewtest.configuration.dagger.module.ActivityModule;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected void inject() {
        ((InterviewTestApplication) getApplication()).getApplicationComponent().inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());

        inject();
        ButterKnife.bind(this);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((InterviewTestApplication)getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    public abstract int getLayoutResource();

    public void showError(int messageId) {
        Snackbar errorSnackbar = Snackbar
                .make(findViewById(android.R.id.content), messageId, Snackbar.LENGTH_LONG);

        View view = errorSnackbar.getView();
        TextView snackText = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        if (null != snackText) {
            snackText.setTextColor(getResources().getColor(R.color.dark_primary));
        }

        errorSnackbar.show();
    }
}
