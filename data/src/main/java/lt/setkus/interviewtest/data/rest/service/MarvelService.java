package lt.setkus.interviewtest.data.rest.service;

import lt.setkus.interviewtest.data.rest.response.Comics;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public interface MarvelService {

    @GET("/v1/public/comics?format=comic&orderBy=-onsaleDate")
    Observable<Comics> getComics(@Query("offset") Integer offset, @Query("ts") long timestamp,  @Query("apikey") String apiKey, @Query("hash") String hash);
}
