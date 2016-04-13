package lt.setkus.interviewtest.data.repository;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lt.setkus.interviewtest.data.rest.MarvelApiConfiguration;
import lt.setkus.interviewtest.data.rest.RestClient;
import lt.setkus.interviewtest.data.rest.response.Comics;
import lt.setkus.interviewtest.data.rest.response.Result;
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

        if (HttpURLConnection.HTTP_OK == source.getCode() && source.getData().getCount() > 0) {
            for (Result result : source.getData().getResults()) {
                String thumbPath = result.getThumbnail().getPath() +"."+ result.getThumbnail().getExtension();
                ComicDomain comicDomain = new ComicDomain(result.getTitle(), thumbPath, result.getId());

                comics.add(comicDomain);
            }
        }

        return comics;
    };

    public MarvelDataRepository(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Observable<List<ComicDomain>> getComics(Integer offset) {
        MarvelApiConfiguration marvelApiConfiguration = MarvelApiConfiguration.buildConfiguration();
        return restClient.getMarvelService()
                .getComics(offset, marvelApiConfiguration.getTimeStamp(), marvelApiConfiguration.getPublicKey(), marvelApiConfiguration.getHash())
                .map(mapResponseToDomain);
    }
}
