package eu.oora.marvel.model.api

import retrofit2.adapter.rxjava.Result
import java.net.HttpURLConnection

class ApiResult<T>(val result: Result<T>) {

  val body: T
    get() {
      return result.response().body()
    }

  val error: String
    get() {
      return result.response().errorBody().string()
    }

  val isError: Boolean
    get() = result.isError || isValidationError

  val isValidationError: Boolean
    get() {
      return !result.isError && result.response().code() !in HttpURLConnection.HTTP_OK..HttpURLConnection.HTTP_PARTIAL
    }
}