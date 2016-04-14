package com.unaimasa.marvelcomics.util;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.unaimasa.marvelcomics.MarvelComicsApp;
import com.unaimasa.marvelcomics.base.SingleToast;

/**
 * Created by unai.masa on 13/04/2016.
 */
public class CommonUtils {

    public static void showToast(String text, int duration) {
        SingleToast.show(text, duration);
    }

    public static void showToast(@StringRes int resId, int duration) {
        SingleToast.show(MarvelComicsApp.getInstance().getText(resId), duration);
    }

    public static void showToast(String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public static void showToast(int resId) {
        showToast(resId, Toast.LENGTH_SHORT);
    }

}
