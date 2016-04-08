package me.smorenburg.marvelcomics.base.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.karumi.marvelapiclient.MarvelApiConfig;

import me.smorenburg.marvelcomics.BuildConfig;
import me.smorenburg.marvelcomics.base.MarvelAppFunctionality;

/**
 * Created by Ivor Smorenburg Aguado on 08/04/2016.
 */
public class MarvelApiTaskRequest extends AsyncTask<Object, Object, Object> implements MarvelAppFunctionality {

    protected Context context;
    private MarvelApiConfig marvelApiConfig;

    private OnMarvelRequestTaskComplete onMarvelRequestTaskComplete;

    private View[] viewsToLock = new View[0];

    public MarvelApiTaskRequest(Context context) {
        this.context = context;
    }

    public void setViewsToLock(View... _viewsToLock) {
        viewsToLock = _viewsToLock;
    }

    private void lockViews() {
        for (int i = 0; i < viewsToLock.length; i++) {
            viewsToLock[i].setEnabled(false);
        }
    }

    private void unLockViews() {
        for (int i = 0; i < viewsToLock.length; i++) {
            viewsToLock[i].setEnabled(true);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        lockViews();
    }

    @Override
    protected Object doInBackground(Object... params) {
        return null;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        if (onMarvelRequestTaskComplete != null) {
            onMarvelRequestTaskComplete.getData(result, (result == null));
        }
        unLockViews();
    }

    @Override
    public MarvelApiConfig getMarvelApiConfig() {
        if (marvelApiConfig != null)
            return marvelApiConfig;
        if (BuildConfig.DEBUG) {
            return (marvelApiConfig = new MarvelApiConfig.Builder(BuildConfig.MARVEL_PUBLIC_KEY, BuildConfig.MARVEL_PRIVATE_KEY).debug().build());
        } else {
            return (marvelApiConfig = new MarvelApiConfig.Builder(BuildConfig.MARVEL_PUBLIC_KEY, BuildConfig.MARVEL_PRIVATE_KEY).build());
        }
    }

    public void setOnMarvelRequestTaskComplete(OnMarvelRequestTaskComplete onMarvelRequestTaskComplete) {
        this.onMarvelRequestTaskComplete = onMarvelRequestTaskComplete;
    }


    public interface OnMarvelRequestTaskComplete {
        void getData(Object data, boolean hasFail);
    }
}
