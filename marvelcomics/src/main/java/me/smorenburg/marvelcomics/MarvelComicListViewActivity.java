package me.smorenburg.marvelcomics;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.trey.marvel.model.api.manager.ComicManager;
import com.trey.marvel.model.api.request.ComicRequest;
import com.trey.marvel.model.api.request.RequestSignature;
import com.trey.marvel.model.api.response.ServiceResponse;
import com.trey.marvel.model.api.vo.Comic;

import java.util.LinkedList;
import java.util.List;

import me.smorenburg.marvelcomics.base.MarverActivityBase;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MarvelComicListViewActivity extends MarverActivityBase {

    private ListView comicsListView;
    private TextView istViewPlaceHolder;

    private List<Comic> comics = new LinkedList<>();
    private int lastOffSet = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marvel_comic_list_view);

        comicsListView = (ListView) findViewById(R.id.comicsListView);
        istViewPlaceHolder = (TextView) findViewById(R.id.listViewPlaceHolder);

    }

    public void refreshComics(View v) {
        istViewPlaceHolder.setVisibility(View.VISIBLE);
        ComicRequest request = new ComicRequest(RequestSignature.create());
        request.setOffset(lastOffSet);
        request.setLimit((lastOffSet += lastOffSet + 20));
        request.setOrderBy(ComicRequest.OrderBy.OnSaleDate);
        ComicManager comicManager = new ComicManager();

        comicManager.listComics(request, new Callback<ServiceResponse<Comic>>() {
            @Override
            public void success(ServiceResponse<Comic> comicServiceResponse, Response response) {
                for (Comic comic : comicServiceResponse.data.results) {
                    comics.add(comic);
                    Log.i("Comic", comic.title);
                    istViewPlaceHolder.append("\n " + comic.title + ",");

                }

                istViewPlaceHolder.setVisibility(View.GONE);
            }

            @Override
            public void failure(RetrofitError error) {
                istViewPlaceHolder.setVisibility(View.VISIBLE);
                Log.i("Comic", error.getMessage());
                if (!istViewPlaceHolder.getText().toString().contains(error.getResponse().getReason())) {
                    istViewPlaceHolder.append("\n " + error.getResponse().getReason() + ",");
                }
            }
        });

        istViewPlaceHolder.setVisibility(View.VISIBLE);
    }
}
