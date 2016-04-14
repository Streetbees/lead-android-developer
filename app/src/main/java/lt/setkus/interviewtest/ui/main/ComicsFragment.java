package lt.setkus.interviewtest.ui.main;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lt.setkus.interviewtest.app.R;
import lt.setkus.interviewtest.model.ComicModel;
import lt.setkus.interviewtest.presenter.ComicsPresenter;
import lt.setkus.interviewtest.presenter.view.ComicView;
import lt.setkus.interviewtest.ui.BaseActivity;
import lt.setkus.interviewtest.ui.BaseFragment;
import lt.setkus.interviewtest.ui.widget.ComicsRecyclerViewScrollListener;
import lt.setkus.interviewtest.ui.widget.ItemClickSupport;
import lt.setkus.interviewtest.util.BitmapUtils;
import lt.setkus.interviewtest.util.ExceptionsUtil;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public class ComicsFragment extends BaseFragment implements ComicView {

    interface CameraEventsListener {
        void dispatchCameraIntent(int itemPosition);
    }

    private CameraEventsListener cameraEventsListener;

    private static final int SPAN_COUNT = 3;

    private ComicsAdapter comicsAdapter = new ComicsAdapter();

    @Bind(R.id.comics_list)
    RecyclerView comicsList;

    @Inject
    ComicsPresenter comicsPresenter;

    public static ComicsFragment newInstance(Bundle arguments) {
        ComicsFragment comicsFragment = new ComicsFragment();
        comicsFragment.setArguments(arguments);

        return comicsFragment;
    }

    private void init() {
        ((MainActivity) getActivity()).getComponent().inject(this);

        comicsPresenter.setView(this);
        comicsPresenter.getComics();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!(getActivity() instanceof CameraEventsListener)) {
            throw new IllegalArgumentException(getActivity().getClass().getSimpleName() +" must implement CameraEventsListener");
        }

        cameraEventsListener = (CameraEventsListener) getActivity();
        comicsAdapter.setCameraEventListener(cameraEventsListener);

        if (null == savedInstanceState) {
            init();
        }

//        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState) {
        final View layout = layoutInflater.inflate(R.layout.fragment_comics, parent, false);

        ButterKnife.bind(this, layout);

        ItemClickSupport.addTo(comicsList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {

            private ViewSwitcher lastClicked;

            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                if (null != lastClicked) {
                    lastClicked.showNext();
                }

                lastClicked = (ViewSwitcher) view;

                Bitmap copy = BitmapUtils.getBitmapFromView(view.findViewById(R.id.poster));
                Bitmap blured = BitmapUtils.blur(getContext(), copy);

                View next = ((ViewSwitcher) view).getNextView();

                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                    next.setBackgroundDrawable(new BitmapDrawable(getResources(), blured));
                } else {
                    next.setBackground(new BitmapDrawable(getResources(), blured));
                }

                ((ViewSwitcher) view).showNext();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), SPAN_COUNT);

        comicsList.setLayoutManager(gridLayoutManager);
        comicsList.addOnScrollListener(new ComicsRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                comicsPresenter.getComics(totalItemsCount);
            }
        });

        comicsList.setHasFixedSize(true);
        comicsList.setAdapter(comicsAdapter);

        return layout;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_load:
                comicsPresenter.getComics(20);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void renderComics(List<ComicModel> comicModels) {
        if (comicsAdapter.getItemCount() == 0) {
            comicsAdapter.addComics(comicModels);
        } else {
            comicsAdapter.append(comicModels);
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(Throwable throwable) {
        ((BaseActivity) getActivity()).showError(ExceptionsUtil.getMessageByException(throwable));
    }

    public void updatePosterAtPosition(Bitmap newBitmap, int positionInAdapter) {
        View view = comicsList.getChildAt(positionInAdapter);
        if (null != view) {
            ViewSwitcher viewSwitcher = (ViewSwitcher) view;
            SimpleDraweeView drawee = (SimpleDraweeView) viewSwitcher.findViewById(R.id.poster);
            drawee.setImageBitmap(BitmapUtils.scaleTo(newBitmap, viewSwitcher));
        }
    }

    static class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ComicViewHolder> {

        private List<ComicModel> comicModelList = new ArrayList<ComicModel>();
        private CameraEventsListener cameraEventListener;

        public void setCameraEventListener(CameraEventsListener cameraEventListener) {
            this.cameraEventListener = cameraEventListener;
        }

        public void addComics(List<ComicModel> comicModelList) {
            this.comicModelList.addAll(comicModelList);
            notifyDataSetChanged();
        }

        public void append(List<ComicModel> comicModelList) {
            int currentSize = this.comicModelList.size();
            this.comicModelList.addAll(comicModelList);

            notifyItemRangeInserted(currentSize, this.comicModelList.size() - 1);
        }

        @Override
        public ComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comics_list, parent, false);
            return new ComicViewHolder(itemLayout, cameraEventListener);
        }

        @Override
        public void onBindViewHolder(ComicViewHolder holder, int position) {
            holder.setComic(comicModelList.get(position));
        }

        @Override
        public int getItemCount() {
            return comicModelList.size();
        }

        static class ComicViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.poster)
            SimpleDraweeView poster;

            @Bind(R.id.title)
            TextView title;

            @Bind(R.id.camera_button)
            Button cameraButton;

            ComicModel comicModel;

            final CameraEventsListener cameraEventsListner;

            public ComicViewHolder(View itemView, CameraEventsListener cameraEventsListner) {
                super(itemView);

                this.cameraEventsListner = cameraEventsListner;

                ButterKnife.bind(this, itemView);
            }

            public void setComic(ComicModel comic) {
                this.comicModel = comic;

                poster.setImageURI(Uri.parse(comicModel.getThumbnailPath()));
                title.setText(comicModel.getTitle());
            }

            @OnClick(R.id.camera_button)
            public void onCameraButtonClick() {
                cameraEventsListner.dispatchCameraIntent(getAdapterPosition());
            }
        }
    }
}
