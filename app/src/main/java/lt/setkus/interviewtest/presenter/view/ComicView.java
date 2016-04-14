package lt.setkus.interviewtest.presenter.view;

import java.util.List;

import lt.setkus.interviewtest.model.ComicModel;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public interface ComicView extends DataView {
    void renderComics(List<ComicModel> comicModels);
}
