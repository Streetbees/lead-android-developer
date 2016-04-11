package me.smorenburg.marvelcomics.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.karumi.marvelapiclient.model.ComicDto;
import com.karumi.marvelapiclient.model.ComicsDto;
import com.karumi.marvelapiclient.model.MarvelImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.text.ParseException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import me.smorenburg.marvelcomics.R;

/**
 * Created by Ivor Smorenburg Aguado on 08/04/2016.
 */
public class ComicsAdapter extends BaseAdapter {

    private final DisplayImageOptions options;
    private Context context;
    private List<ComicDto> comicDtos;

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public ComicsAdapter(Context _context, List<ComicDto> _comicDtos) {
        context = _context;
        comicDtos = _comicDtos;

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(android.R.drawable.stat_sys_download)
                .showImageForEmptyUri(android.R.drawable.screen_background_dark_transparent)
                .showImageOnFail(android.R.drawable.stat_notify_error)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .considerExifParams(true)
//                .displayer(new CircleBitmapDisplayer(Color.WHITE, 5))
                .build();
    }

    @Override
    public int getCount() {
        return comicDtos.size();
    }

    @Override
    public Object getItem(int position) {
        return comicDtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        try {
            return Integer.parseInt(comicDtos.get(position).getId());
        } catch (NumberFormatException e) {
            return position;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ComicDto comicDto = comicDtos.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.comic_item_list, null);
        ImageView comicCover = (ImageView) convertView.findViewById(R.id.comicCover);
        TextView tittle = (TextView) convertView.findViewById(R.id.title);
        TextView releaseDate = (TextView) convertView.findViewById(R.id.releaseDate);
        tittle.setText(comicDto.getTitle());
        releaseDate.append(comicDto.getDates().get(0).getDate());
        try {
            ImageLoader.getInstance().displayImage(comicDto.getImages().get(0).getImageUrl(MarvelImage.Size.STANDARD_MEDIUM), comicCover, options, animateFirstListener);
        } catch (IndexOutOfBoundsException e) {
        }
        return convertView;
    }

    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }
}
