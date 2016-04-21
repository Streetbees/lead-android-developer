package com.streetbees.clementetort.marvellous.ui.adapters;

/**
 * Created by clemente.tort on 20/04/16.
 */

import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.streetbees.clementetort.marvellous.R;
import com.streetbees.clementetort.marvellous.marvel.models.Comic;
import com.streetbees.clementetort.marvellous.ui.utils.ImageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by clemente.tort on 20/04/16.
 */
public class ComicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int COMIC = 0;
    private static final int LOADING = 1;
    private static final int ERROR = 2;
    private final ComicAdapterListener listener;

    List<Comic> comicList;
    boolean loading;
    boolean error;
    String errorMessage;

    public ComicAdapter(ComicAdapterListener listener) {
        comicList = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case COMIC:
                View comicView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comic_item, parent, false);
                return new ComicViewHolder(comicView, listener);
            case LOADING:
                View loadingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comics_loading_view, parent, false);
                return new LoadingViewHolder(loadingView);
            case ERROR:
                View errorView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comics_load_error, parent, false);
                return new ErrorViewHolder(errorView, listener);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case COMIC:
                ComicViewHolder comicViewHolder = (ComicViewHolder) holder;
                comicViewHolder.setItem(comicList.get(position));
                break;
            case LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) loadingViewHolder.itemView.getLayoutParams();
                layoutParams.setFullSpan(true);
                break;
            case ERROR:
                ErrorViewHolder errorViewHolder = (ErrorViewHolder) holder;
                StaggeredGridLayoutManager.LayoutParams layoutParams2 = (StaggeredGridLayoutManager.LayoutParams) errorViewHolder.itemView.getLayoutParams();
                layoutParams2.setFullSpan(true);
                errorViewHolder.setMessage(errorMessage);
                break;
        }
    }

    public void setError(String errorMessage) {
        this.errorMessage = errorMessage;

        if (loading || this.error)
            notifyItemChanged(comicList.size());
        else
            notifyItemInserted(comicList.size());

        this.loading = false;
        this.error = true;
    }

    public void setLoading() {
        if (loading || this.error)
            notifyItemChanged(comicList.size());
        else
            notifyItemInserted(comicList.size());
        this.error = false;
        this.loading = true;
    }

    public void addAll(List<Comic> comics) {
        error = false;
        loading = false;
        notifyItemRemoved(comicList.size());
        comicList.addAll(comics);
        notifyItemRangeInserted(comicList.size(), comics.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (position < comicList.size())
            return COMIC;
        else if (error)
            return ERROR;
        else
            return LOADING;
    }

    @Override
    public int getItemCount() {
        return comicList.size() + ((error || loading) ? 1 : 0);
    }

    public int getComicCount() {
        return comicList.size();
    }

    public static class ComicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.textView)
        TextView textView;

        @Bind(R.id.imageView)
        ImageView imageView;

        private Comic comic;
        private final ComicAdapterListener listener;

        public ComicViewHolder(View itemView, ComicAdapterListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        public void setItem(Comic comic) {
            this.comic = comic;
            textView.setText(comic.title);

            File file = getFile(comic.id);
            if (!file.exists())
                ImageUtils.setImage(imageView, comic.thumbnail.getPath(), R.drawable.comic_thumbnail_place_holder);
            else
                ImageUtils.setImage(imageView, FileProvider.getUriForFile(imageView.getContext(), "com.streetbees.clementetort.marvellous.fileprovider", file), R.drawable.comic_thumbnail_place_holder);
        }

        @Override
        public void onClick(View v) {
            listener.onComicClicked(comic);
        }

        public File getFile(long id) {
            File imagePath = new File(textView.getContext().getFilesDir(), "images");
            return new File(imagePath, String.valueOf(id) + ".jpg");
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class ErrorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.errorMessage)
        TextView errorMessage;

        @Bind(R.id.retryButton)
        Button retryButton;

        private final ComicAdapterListener listener;

        public ErrorViewHolder(View itemView, ComicAdapterListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            retryButton.setOnClickListener(this);
            this.listener = listener;
        }

        public void setMessage(String message) {
            errorMessage.setText(message);
        }

        @Override
        public void onClick(View v) {
            listener.onRequestRetry();
        }
    }

    public interface ComicAdapterListener {
        void onRequestRetry();

        void onComicClicked(Comic comic);
    }
}

