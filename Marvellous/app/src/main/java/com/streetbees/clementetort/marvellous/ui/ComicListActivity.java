package com.streetbees.clementetort.marvellous.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.streetbees.clementetort.marvellous.BuildConfig;
import com.streetbees.clementetort.marvellous.R;
import com.streetbees.clementetort.marvellous.marvel.models.Comic;
import com.streetbees.clementetort.marvellous.marvel.network.MarvelResponse;
import com.streetbees.clementetort.marvellous.marvel.network.MarvelService;
import com.streetbees.clementetort.marvellous.models.Credentials;
import com.streetbees.clementetort.marvellous.ui.adapters.ComicAdapter;
import com.streetbees.clementetort.marvellous.ui.utils.EndlessRecyclerViewScrollListener;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ComicListActivity extends RealmActivity implements ComicAdapter.ComicAdapterListener, DropboxDialogFragment.DropboxDialogFragmentListener {
    private static final String COMIC = "comic";
    private static final String FOC_DATE = "focDate";
    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int PERMISSIONS_REQUEST_CAMERA = 1;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private ComicAdapter comicAdapter;
    private static final int COMIC_QUANTITY = 20;

    DropboxAPI<AndroidAuthSession> mDBApi;
    private Comic lastSelectedComic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_list);
        ButterKnife.bind(this);
        // TODO: We must calculate the number of rows based on the width!
        StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        comicAdapter = new ComicAdapter(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMoreComics();
            }
        });

        recyclerView.setAdapter(comicAdapter);
        initDropbox();
        loadMoreComics();
    }

    private void initDropbox() {
        String accessToken = null;

        Credentials credentials = realm.where(Credentials.class).findFirst();
        if (credentials != null)
            accessToken = credentials.getDropboxToken();

        AppKeyPair appKeys = new AppKeyPair(BuildConfig.DROPBOX_APP_ID, BuildConfig.DROPBOX_APP_SECRET);
        // We should supply the token again, but during testing was not working
        AndroidAuthSession session = new AndroidAuthSession(appKeys, accessToken);
        mDBApi = new DropboxAPI<>(session);
    }

    protected void onResume() {
        super.onResume();

        if (mDBApi.getSession().authenticationSuccessful()) {
            try {
                // Required to complete auth, sets the access token on the session
                mDBApi.getSession().finishAuthentication();

                String accessToken = mDBApi.getSession().getOAuth2AccessToken();
                storeAccessToken(accessToken);

            } catch (IllegalStateException e) {
                Log.i("DbAuthLog", "Error authenticating", e);
            }
        }
    }

    private void storeAccessToken(String accessToken) {
        Credentials credentials = realm.where(Credentials.class).findFirst();

        realm.beginTransaction();
        if (credentials == null)
            credentials = realm.createObject(Credentials.class);

        credentials.setDropboxToken(accessToken);

        realm.commitTransaction();
    }


    private void loadMoreComics() {
        comicAdapter.setLoading();
        MarvelService.getService().getComics(COMIC, FOC_DATE, COMIC_QUANTITY, comicAdapter.getComicCount()).enqueue(new Callback<MarvelResponse<Comic>>() {
            @Override
            public void onResponse(Response<MarvelResponse<Comic>> response, Retrofit retrofit) {
                comicAdapter.addAll(response.body().data.results);
            }

            @Override
            public void onFailure(Throwable t) {
                comicAdapter.setError(getString(R.string.generic_error));
            }
        });
    }

    @Override
    public void onRequestRetry() {
        loadMoreComics();
    }

    @Override
    public void onComicClicked(Comic comic) {
        this.lastSelectedComic = comic;

        if (!needsLogInDropbox())
            DropboxDialogFragment.newInstance().show(getSupportFragmentManager(), "Dropbox dialog");
        else
            requestCameraPermissions();
    }

    private void takePicture() {
        Uri imageURI = generateImageFile(lastSelectedComic.id);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
        // Resolve the package
        String packageName = takePictureIntent.resolveActivity(getPackageManager()).getPackageName();
        // grant permissions
        grantUriPermission(packageName, imageURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

//        List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
//        for (ResolveInfo resolveInfo : resInfoList) {
//            String packageName2 = resolveInfo.activityInfo.packageName;
//            grantUriPermission(packageName2, imageURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        }
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            
        }else{

        }
    }

    private boolean needsLogInDropbox() {
        return mDBApi.getSession().authenticationSuccessful();
    }

    @Override
    public void onDropboxLogin() {
        mDBApi.getSession().startOAuth2Authentication(this);
    }

    // Creates images in the internal storage
    public Uri generateImageFile(long id) {
        File videosDir = new File(getFilesDir() + File.separator + "images");
        videosDir.mkdirs();

        File imagePath = new File(getFilesDir(), "images");
        File newFile = new File(imagePath, String.valueOf(id) + ".jpg");
        return FileProvider.getUriForFile(this, "com.streetbees.clementetort.marvellous.fileprovider", newFile);
    }

    private void requestCameraPermissions(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSIONS_REQUEST_CAMERA);
            }
        }else{
            takePicture();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    takePicture();
                }
            }
        }
    }
}
