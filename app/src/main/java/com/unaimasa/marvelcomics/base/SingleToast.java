package com.unaimasa.marvelcomics.base;

import android.text.TextUtils;
import android.widget.Toast;

import com.unaimasa.marvelcomics.MarvelComicsApp;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by unai.masa on 14/10/2015.
 */
public class SingleToast {

    private static final Set<CharSequence> sCurrentToasts = new HashSet<>();

    private static Toast sInstance = Toast.makeText(MarvelComicsApp.getInstance(), "", Toast.LENGTH_SHORT);

    public static void show(CharSequence text, int duration) {
        if (sInstance.getView() == null) {
            // not initialized somehow - use not single instance approach (e.g. in tests get view is null)
            Toast.makeText(MarvelComicsApp.getInstance(), text, duration).show();
            return;
        }
        if (!sInstance.getView().isShown()) {
            sCurrentToasts.clear();
        }
        if (!TextUtils.isEmpty(text) && !sCurrentToasts.contains(text)) {
            sCurrentToasts.add(text);
            sInstance.setText(text);
            sInstance.setDuration(duration);
            sInstance.show();
        }
    }

}
