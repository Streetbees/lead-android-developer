package eu.oora.marvel.model

import eu.oora.marvel.dependency.scope.PerApplicationScope
import eu.oora.marvel.extenstion.toModel
import eu.oora.marvel.model.api.Api
import eu.oora.marvel.model.api.ApiResult
import eu.oora.marvel.model.api.response.GetComicsListResponse
import eu.oora.marvel.model.model.ComicBookModel
import retrofit2.adapter.rxjava.Result
import rx.Observable
import java.util.ArrayList
import javax.inject.Inject

@PerApplicationScope
class ComicBookService {
  @Inject
  lateinit var api: Api

  @Inject
  constructor()

  fun getComicBookList(): Observable<ComicBookModel> {
    return api.getComicsList()
      .flatMap({ response -> response?.toObservable() })
  }

  fun Result<GetComicsListResponse>.toObservable(): Observable<ComicBookModel> {
    val output = ArrayList<ComicBookModel>()

    val result = ApiResult(this)
    if (!result.isError) {
      for (entry in result.body.data.results) {
        output.add(entry.toModel())
      }
    }

    return Observable.from(output)
  }
}