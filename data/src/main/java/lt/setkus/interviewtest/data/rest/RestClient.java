package lt.setkus.interviewtest.data.rest;

import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.File;

import lt.setkus.interviewtest.data.BuildConfig;
import lt.setkus.interviewtest.data.rest.service.MarvelService;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * @author <a href="mailto:robertas.setkus@gmail.com">robertas</a>
 */
public class RestClient {

    private final Retrofit retrofit;
    private final MarvelService marvelService;

    private static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10 * 1024 * 1024;

    private RestClient(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient();

        File baseCacheDir = context.getCacheDir();
        if (null != baseCacheDir) {
            File cacheDir = new File(baseCacheDir, "OkHttpCacheDir");
            Cache cache = new Cache(cacheDir, HTTP_RESPONSE_DISK_CACHE_MAX_SIZE);
            okHttpClient.setCache(cache);
        }

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient.interceptors().add(httpLoggingInterceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        marvelService = retrofit.create(MarvelService.class);
    }

    public static RestClient newInstance(Context context) {
        return new RestClient(context);
    }

    public MarvelService getMarvelService() {
        return this.marvelService;
    }
}
