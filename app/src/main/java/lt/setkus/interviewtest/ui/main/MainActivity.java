package lt.setkus.interviewtest.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
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
public class MainActivity extends BaseActivity implements HasComponent<ComicsComponent>, ComicsFragment.CameraEventsListener {

    private static final String COMICS_FRAGMENT_TAG = "comics_fragment";
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private ComicsComponent comicsComponent;

    private int lastKnownComicItemPosition;

    private void initComicsComponent() {
        comicsComponent = DaggerComicsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .comicsModule(new ComicsModule())
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Fragment fragment = getSupportFragmentManager().findFragmentByTag(COMICS_FRAGMENT_TAG);
            if (fragment instanceof ComicsFragment) {
                ((ComicsFragment) fragment).updatePosterAtPosition(imageBitmap, lastKnownComicItemPosition);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null == savedInstanceState) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.container, ComicsFragment.newInstance(new Bundle()), COMICS_FRAGMENT_TAG)
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

    @Override
    public void dispatchCameraIntent(int itemPosition) {
        lastKnownComicItemPosition = itemPosition;

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
