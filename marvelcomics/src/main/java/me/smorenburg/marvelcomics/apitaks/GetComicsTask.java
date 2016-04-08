package me.smorenburg.marvelcomics.apitaks;

import android.content.Context;
import android.util.Log;

import com.karumi.marvelapiclient.ComicApiClient;
import com.karumi.marvelapiclient.MarvelApiException;
import com.karumi.marvelapiclient.model.ComicDto;
import com.karumi.marvelapiclient.model.ComicsDto;
import com.karumi.marvelapiclient.model.ComicsQuery;
import com.karumi.marvelapiclient.model.MarvelResponse;
import com.karumi.marvelapiclient.model.OrderBy;

import java.util.List;

import me.smorenburg.marvelcomics.base.tasks.MarvelApiTaskRequest;

/**
 * Created by Ivor Smorenburg Aguado on 08/04/2016.
 */
public class GetComicsTask extends MarvelApiTaskRequest {

    public GetComicsTask(Context context) {
        super(context);
    }

    @Override
    protected List<ComicDto> doInBackground(Object... params) {
        try {
            ComicApiClient comicApiClient = new ComicApiClient(getMarvelApiConfig());
            ComicsQuery query = ComicsQuery.Builder.create().withOrderBy(OrderBy.MODIFIED,true).withOffset((int) params[0]).withLimit((int)params[1]).build();
            MarvelResponse<ComicsDto> comicsDtoMarvelResponse = comicApiClient.getAll(query);
            return comicsDtoMarvelResponse.getResponse().getComics();
        } catch (MarvelApiException e) {
            Log.e("GetComicsTask", e.getMessage());
            return null;
        }
    }
}
