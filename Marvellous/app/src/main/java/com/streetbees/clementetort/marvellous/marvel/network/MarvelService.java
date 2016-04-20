package com.streetbees.clementetort.marvellous.marvel.network;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.streetbees.clementetort.marvellous.BuildConfig;
import com.streetbees.clementetort.marvellous.marvel.models.Comic;

import java.io.IOException;

import retrofit.Call;
import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by clemente.tort on 20/04/16.
 */
public class MarvelService {
    private static API instance;
    private static final String FORMAT_TYPE = "formatType";
    private static final String ORDER_BY = "orderBy";
    private static final String LIMIT = "limit";
    private static final String OFFSET = "offset";

    private static void initApiService() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        // The following headers are a hack to grant access to the API
                        .header("Host", "gateway.marvel.com")
                        .header("Origin", "https://developer.marvel.com")
                        .header("Referer", "https://developer.marvel.com/docs")
                        .method(original.method(), original.body())
                        .build();

                HttpUrl url = request.httpUrl().newBuilder().addQueryParameter("apikey", BuildConfig.MARVEL_KEY).build();
                request = request.newBuilder().url(url).build();

                return chain.proceed(request);
            }
        });
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.interceptors().add(interceptor);
        }

        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, BuildConfig.DEBUG);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .client(okHttpClient)
                .baseUrl(BuildConfig.MARVEL_URL).build();

        instance = retrofit.create(API.class);
    }

    public static API getService() {
        if (instance == null)
            initApiService();
        return instance;
    }

    public interface API {
        @GET("/v1/public/comics")
        Call<MarvelResponse<Comic>> getComics(@Query(FORMAT_TYPE) String format, @Query(ORDER_BY) String orderBy, @Query(LIMIT) int limit, @Query(OFFSET) int offset);
    }
}
