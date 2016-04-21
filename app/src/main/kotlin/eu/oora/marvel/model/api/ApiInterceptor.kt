package eu.oora.marvel.model.api

import eu.oora.marvel.extenstion.md5
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.io.IOException

class ApiInterceptor(properties: ApiProperties) : Interceptor {
  private val PARAM_API_KEY = "apikey"
  private val PARAM_TIMESTAMP = "ts"
  private val PARAM_HASH = "hash"

  private val mApiKey: String
  private val mTimestamp: String
  private val mHash: String

  init {
    mApiKey = properties.publicKey
    mTimestamp = "1"
    mHash = mTimestamp.plus(properties.privateKey).plus(properties.publicKey).md5()
  }

  @Throws(IOException::class)
  override fun intercept(chain: Chain): Response {
    val original = chain.request()

    val url = original.url().newBuilder()
      .addQueryParameter(PARAM_API_KEY, mApiKey)
      .addQueryParameter(PARAM_TIMESTAMP, mTimestamp)
      .addQueryParameter(PARAM_HASH, mHash)
      .build()

    val request = original.newBuilder()
      .url(url)
      .method(original.method(), original.body())
      .build()

    return chain.proceed(request)
  }
}
