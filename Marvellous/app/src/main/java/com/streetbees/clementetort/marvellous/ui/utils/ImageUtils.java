package com.streetbees.clementetort.marvellous.ui.utils;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.streetbees.clementetort.marvellous.BuildConfig;

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
}