package com.unaimasa.marvelcomics.section.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.unaimasa.marvelcomics.MarvelComicsApp;
import com.unaimasa.marvelcomics.R;
import com.unaimasa.marvelcomics.entity.ComicResponse;
import com.unaimasa.marvelcomics.util.CommonUtils;

import java.io.File;

/**
 * Created by UnaiMasa on 13/04/2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ComicViewHolder>{

    static Activity mActivity;
    ComicResponse mComicResponse;
    ImageLoader imageLoader;

    public RVAdapter(Activity activity, ComicResponse comicResponse){
        mActivity = activity;
        this.mComicResponse = comicResponse;
        this.imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(MarvelComicsApp.getInstance()));
    }

    @Override
    public ComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ComicViewHolder pvh = new ComicViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ComicViewHolder holder, int position) {
        holder.personName.setText(mComicResponse.data.results[position].title);
        holder.personDescription.setText(mComicResponse.data.results[position].description);
        if (mComicResponse.data.results[position].images.length == 0){
            imageLoader.displayImage(MarvelComicsApp.getInstance().getResources().getResourceName(R.mipmap.ic_launcher), holder.personPhoto);
        }else{
            String thumbnailURL = mComicResponse.data.results[position].thumbnail.path + "." + mComicResponse.data.results[position].thumbnail.extension;
            imageLoader.displayImage(thumbnailURL, holder.personPhoto);
        }
    }

    @Override
    public int getItemCount() {
        return Integer.valueOf(mComicResponse.data.count);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ComicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cv;
        TextView personName;
        TextView personDescription;
        ImageView personPhoto;

        ComicViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personDescription = (TextView)itemView.findViewById(R.id.person_description);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            // here you use position
            String position = String.valueOf(getAdapterPosition());

            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));

            // start the image capture Intent
            mActivity.startActivityForResult(intent, 100);
        }
    }
}
