package lt.setkus.interviewtest.domain.repository;

import java.util.List;

import lt.setkus.interviewtest.domain.domain.ComicDomain;
import rx.Observable;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public interface MarvelRepository {
    Observable<List<ComicDomain>> getComics(Integer offset);
}
