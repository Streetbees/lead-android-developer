package me.smorenburg.marvelcomics;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.karumi.marvelapiclient.model.ComicDto;
import com.karumi.marvelapiclient.model.ComicsDto;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import me.smorenburg.marvelcomics.adapters.ComicsAdapter;
import me.smorenburg.marvelcomics.apitaks.GetComicsTask;
import me.smorenburg.marvelcomics.base.MarvelActivityBase;
import me.smorenburg.marvelcomics.base.tasks.MarvelApiTaskRequest;

public class MarvelComicListViewActivity extends MarvelActivityBase implements MarvelApiTaskRequest.OnMarvelRequestTaskComplete {

    private ListView comicsListView;
    private TextView istViewPlaceHolder;

    private Map<String, ComicDto> comics = new HashMap<>();

    private int lastOffSet = 0;
    private int offSetIncrement = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marvel_comic_list_view);

        comicsListView = (ListView) findViewById(R.id.comicsListView);
        istViewPlaceHolder = (TextView) findViewById(R.id.listViewPlaceHolder);
    }

    public void refreshComics(View v) {
        GetComicsTask getComicsTask = new GetComicsTask(this);
        getComicsTask.setOnMarvelRequestTaskComplete(this);
        getComicsTask.setViewsToLock(findViewById(R.id.testMakeReq));
        getComicsTask.execute(lastOffSet, (lastOffSet + offSetIncrement));
        lastOffSet = (lastOffSet + offSetIncrement);
        Log.i("lastOffSet", " "+lastOffSet);
    }

    @Override
    public void getData(Object object, boolean hasFail) {
        if (hasFail) {
            Log.i("comics", "failed");
        } else {
            if (object instanceof List) {
                for (ComicDto comicDto : ((List<ComicDto>) object)) {
                    Log.i("ComicDto", comicDto.toString());
                    if (!comics.containsKey(comicDto.getId()))
                        comics.put(comicDto.getId(), comicDto);
                }
                comicsListView.setAdapter(new ComicsAdapter(MarvelComicListViewActivity.this, new LinkedList<>(comics.values())));
                if (comicsListView.getAdapter().getCount()> 0)
                    istViewPlaceHolder.setVisibility(View.INVISIBLE);
            }
        }
    }
}
