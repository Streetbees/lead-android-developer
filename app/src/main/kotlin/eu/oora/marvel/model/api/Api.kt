package eu.oora.marvel.model.api

import eu.oora.marvel.model.api.response.GetComicsListResponse
import retrofit2.adapter.rxjava.Result
import retrofit2.http.GET
import retrofit2.http.Query
import rx.Observable

interface Api {
  @GET("v1/public/comics")
  fun getComicsList(@Query("limit") limit: Int, @Query("offset") offset: Int): Observable<Result<GetComicsListResponse>>
}