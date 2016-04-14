package com.unaimasa.marvelcomics;

import android.app.Application;
import android.content.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by UnaiMasa on 13/04/2016.
 */
public class MarvelComicsApp extends Application {

    private static final Logger sLogger = LoggerFactory.getLogger(MarvelComicsApp.class);

    private static MarvelComicsApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        sLogger.info("Application created - {}", new Date().getTime());
        instance = this;
    }

    public static Context getInstance() {
        return instance.getApplicationContext();
    }

    public static MarvelComicsApp get() {
        return instance;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

}
