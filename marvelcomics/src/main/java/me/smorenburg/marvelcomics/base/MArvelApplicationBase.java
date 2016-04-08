package me.smorenburg.marvelcomics.base;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.trey.marvel.model.api.MarvelApi;

import io.fabric.sdk.android.Fabric;
import me.smorenburg.marvelcomics.BuildConfig;

/**
 * Created by Ivor Smorenburg Aguado on 08/04/2016.
 */
public class MarvelApplicationBase extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        tryRunFabric();
    }

    private void tryRunFabric() {
        if (!BuildConfig.DEBUG)
            Fabric.with(this, new Crashlytics());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MarvelApi.create(BuildConfig.MARVEL_PRIVATE_KEY, BuildConfig.MARVEL_PUBLIC_KEY, base, 5 * 1024 * 1024);
    }
}
