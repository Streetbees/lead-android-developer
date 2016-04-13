package lt.setkus.interviewtest.data.repository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lt.setkus.interviewtest.data.rest.RestClient;
import lt.setkus.interviewtest.data.rest.response.Comics;
import lt.setkus.interviewtest.domain.domain.ComicDomain;
import lt.setkus.interviewtest.domain.repository.MarvelRepository;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public class MarvelDataRepository implements MarvelRepository {

    private final RestClient restClient;

    private Func1<Comics, List<ComicDomain>> mapResponseToDomain = source -> {
        List<ComicDomain> comics = new ArrayList<ComicDomain>();
        return comics;
    };

    @Inject
    public MarvelDataRepository(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Observable<List<ComicDomain>> getComics(Integer offset) {
        return restClient.getMarvelService().getComics(offset).map(mapResponseToDomain);
    }
}
