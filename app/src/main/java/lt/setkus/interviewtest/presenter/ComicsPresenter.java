package lt.setkus.interviewtest.presenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lt.setkus.interviewtest.domain.domain.ComicDomain;
import lt.setkus.interviewtest.domain.interaction.DefaultSubscriber;
import lt.setkus.interviewtest.domain.interaction.GetComicsUseCase;
import lt.setkus.interviewtest.model.ComicModel;
import lt.setkus.interviewtest.presenter.view.ComicView;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public class ComicsPresenter implements Presenter<ComicView> {

    private final GetComicsUseCase getComicsUseCase;
    private ComicView comicView;

    @Inject
    public ComicsPresenter(GetComicsUseCase getComicsUseCase) {
        this.getComicsUseCase = getComicsUseCase;
    }

    @Override
    public void setView(ComicView dataView) {
        this.comicView = dataView;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        getComicsUseCase.unsubscribe();
    }

    public void getComics() {
        getComics(null);
    }

    public void getComics(Integer offset) {
        comicView.showLoading();

        getComicsUseCase.setOffset(offset);
        getComicsUseCase.execute(new GetComicsSubscriber());
    }

    private final class GetComicsSubscriber extends DefaultSubscriber<List<ComicDomain>> {

        @Override
        public void onCompleted() {
            ComicsPresenter.this.comicView.hideLoading();
        }

        @Override
        public void onNext(List<ComicDomain> comicDomains) {
            List<ComicModel> comicModels = new ArrayList<ComicModel>();

            for (ComicDomain comicDomain : comicDomains) {
                comicModels.add(ComicModel.fromComicDomain(comicDomain));
            }

            ComicsPresenter.this.comicView.renderComics(comicModels);
        }

        @Override
        public void onError(Throwable throwable) {
            ComicsPresenter.this.comicView.hideLoading();
            ComicsPresenter.this.comicView.showError(throwable);
        }
    }
}
