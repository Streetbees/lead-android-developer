package com.streetbees.clementetort.marvellous.ui;

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

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ComicListActivity extends RealmActivity implements ComicAdapter.ComicAdapterListener, DropboxDialogFragment.DropboxDialogFragmentListener {
    private static final String COMIC = "comic";
    private static final String FOC_DATE = "focDate";

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private ComicAdapter comicAdapter;
    private static final int COMIC_QUANTITY = 20;

    DropboxAPI<AndroidAuthSession> mDBApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_list);
        ButterKnife.bind(this);
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
        session.startOAuth2Authentication(this);
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
        if (!needsLogInDropbox())
            DropboxDialogFragment.newInstance().show(getSupportFragmentManager(), "Dropbox dialog");
    }

    private boolean needsLogInDropbox() {
        return mDBApi.getSession().authenticationSuccessful();
    }

    @Override
    public void onDropboxLogin() {
        mDBApi.getSession().startOAuth2Authentication(this);
    }
}
