package com.streetbees.clementetort.marvellous.ui.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by clemente.tort on 20/04/16.
 */
public class ImageUtils {
    public static void setImage(ImageView iv, String imageUrl, int placeholder) {
        Glide.clear(iv);
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(iv.getContext())
                    .load(imageUrl)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .error(placeholder)
                    .into(iv);
        } else
            iv.setImageResource(placeholder);
    }

    public static void setImage(ImageView iv, Uri imageUrl, int placeholder) {
        Glide.clear(iv);

        Glide.with(iv.getContext())
                .load(imageUrl)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .error(placeholder)
                .into(iv);
    }
}