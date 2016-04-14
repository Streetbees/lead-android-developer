package com.unaimasa.marvelcomics.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.unaimasa.marvelcomics.MarvelComicsApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by unai.masa on 13/04/2016.
 * Base class for working with SharedPreferences
 */
public class SharedPreferencesUtil {

    private static final Logger logger = LoggerFactory.getLogger(SharedPreferencesUtil.class);

    public static final String PREFERENCES_NAME = "MarvelComicsSP";

    private static SharedPreferencesUtil sInstance = new SharedPreferencesUtil();
    ;
    private SharedPreferences mSharedPreferences;

    private SharedPreferencesUtil() {
        mSharedPreferences = MarvelComicsApp.getInstance().getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferencesUtil getInstance() {
        return sInstance;
    }

    public void clearAll() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public void clearValue(String key) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }


    public void putString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void putFloat(String key, float value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public void putBool(String key, boolean value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        logger.info("put bool key {}, value {}", key, value);
        editor.apply();
    }

    public void putStringSet(String key, Set<String> value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    public String getString(String key) {
        String value = mSharedPreferences.getString(key, "");
        return value;
    }

    public String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    public int getInt(String key) {
        int value = mSharedPreferences.getInt(key, -1);
        return value;
    }

    public int getInt(String key, int defValue) {
        int value = mSharedPreferences.getInt(key, defValue);
        return value;
    }


    public float getFloat(String key, float defaultValue) {
        float value = mSharedPreferences.getFloat(key, defaultValue);
        return value;
    }

    public boolean getBoolean(String key) {
        boolean value = mSharedPreferences.getBoolean(key, false);
        logger.info("get bool key {}, value {}", key, value);
        return value;
    }

    public boolean getBoolean(String key, boolean defValue) {
        boolean value = mSharedPreferences.getBoolean(key, defValue);
        logger.info("get bool key {}, value {}", key, value);
        return value;
    }

    public Set<String> getStringSet(String key) {
        return mSharedPreferences.getStringSet(key, new HashSet<String>());
    }

}
