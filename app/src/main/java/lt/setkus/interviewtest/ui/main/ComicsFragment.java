package lt.setkus.interviewtest.ui.main;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import lt.setkus.interviewtest.app.R;
import lt.setkus.interviewtest.model.ComicModel;
import lt.setkus.interviewtest.presenter.ComicsPresenter;
import lt.setkus.interviewtest.presenter.view.ComicView;
import lt.setkus.interviewtest.ui.BaseFragment;
import lt.setkus.interviewtest.ui.widget.ItemClickSupport;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public class ComicsFragment extends BaseFragment implements ComicView {

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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null == savedInstanceState) {
            init();
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle savedInstanceState) {
        View layout = layoutInflater.inflate(R.layout.fragment_comics, parent, false);

        ButterKnife.bind(this, layout);

        ItemClickSupport.addTo(comicsList).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {

            }
        });

        comicsList.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        comicsList.setHasFixedSize(true);
        comicsList.setAdapter(comicsAdapter);

        return layout;
    }

    @Override
    public void renderComics(List<ComicModel> comicModels) {
        comicsAdapter.addComics(comicModels);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(Throwable throwable) {

    }

    static class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ComicViewHolder> {

        private List<ComicModel> comicModelList = new ArrayList<ComicModel>();

        public void addComics(List<ComicModel> comicModelList) {
            this.comicModelList.addAll(comicModelList);
            notifyDataSetChanged();
        }

        @Override
        public ComicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comics_list, parent, false);
            return new ComicViewHolder(itemLayout);
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

            public ComicViewHolder(View itemView) {
                super(itemView);

                ButterKnife.bind(this, itemView);
            }

            public void setComic(ComicModel comic) {
                poster.setImageURI(Uri.parse(comic.getThumbnailPath()));
                title.setText(comic.getTitle());
            }
        }
    }
}
