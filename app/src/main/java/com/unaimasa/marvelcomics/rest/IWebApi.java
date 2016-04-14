package com.unaimasa.marvelcomics.rest;

import com.unaimasa.marvelcomics.entity.ComicResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by unai.masa on 13/04/2016.
 * Main interface which describe all REST API
 * Retrofit 2.0.1
 */
public interface IWebApi {

    /*
     * Interface for Get Marvel Comics
     */
    @GET("/v1/public/{element}")
    Call<ComicResponse> requestRegister(@Path("element") String element, @Query("orderBy") String sort, @Query("limit") String limit, @Query("apikey") String apikey, @Query("hash") String hash, @Query("ts") String ts);

}


