package com.streetbees.clementetort.marvellous.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;
import com.streetbees.clementetort.marvellous.BuildConfig;
import com.streetbees.clementetort.marvellous.R;
import com.streetbees.clementetort.marvellous.marvel.models.Comic;
import com.streetbees.clementetort.marvellous.marvel.network.MarvelResponse;
import com.streetbees.clementetort.marvellous.marvel.network.MarvelService;
import com.streetbees.clementetort.marvellous.ui.adapters.ComicAdapter;
import com.streetbees.clementetort.marvellous.ui.utils.EndlessRecyclerViewScrollListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class ComicListActivity extends AppCompatActivity implements ComicAdapter.ComicAdapterListener, DropboxDialogFragment.DropboxDialogFragmentListener {
    private static final String COMIC = "comic";
    private static final String FOC_DATE = "focDate";

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private ComicAdapter comicAdapter;
    private static final int COMIC_QUANTITY = 20;

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

        loadMoreComics();
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
        DropboxDialogFragment.newInstance().show(getSupportFragmentManager(), "Dropbox dialog");
    }

    @Override
    public void onDropboxLogin() {
        // In the class declaration section:
        DropboxAPI<AndroidAuthSession> mDBApi;

        AppKeyPair appKeys = new AppKeyPair(BuildConfig.DROPBOX_APP_ID, BuildConfig.DROPBOX_APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);

        mDBApi.getSession().startOAuth2Authentication(this);
    }
}
