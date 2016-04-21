package eu.oora.marvel.dependency.module

import dagger.Module
import dagger.Provides
import eu.oora.marvel.MainApplication
import eu.oora.marvel.dependency.scope.PerApplicationScope
import eu.oora.marvel.model.api.ApiInterceptor
import eu.oora.marvel.model.api.LogInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import java.io.File
import java.security.GeneralSecurityException
import java.util.concurrent.TimeUnit.SECONDS
import javax.net.ssl.SSLContext

@Module
class NetworkModule {
  private val DISK_CACHE_SIZE: Long = 50 * 1024 * 1024 // 50 MB
  private val CONNECTION_TIMEOUT: Long = 10
  private val READ_TIMEOUT: Long = 10
  private val WRITE_TIMEOUT: Long = 10

  @Provides
  @PerApplicationScope
  fun provideCache(application: MainApplication): Cache {
    val cacheDir = File(application.cacheDir, "http")
    return Cache(cacheDir, DISK_CACHE_SIZE)
  }

  @Provides
  @PerApplicationScope
  fun provideOkHttpClient(cache: Cache, apiInterceptor: ApiInterceptor, logInterceptor: LogInterceptor): OkHttpClient {
    val builder = Builder()

    try {
      val sslContext = SSLContext.getInstance("TLS")
      sslContext.init(null, null, null)

      builder.sslSocketFactory(sslContext.socketFactory)
    } catch (e: GeneralSecurityException) {
      throw AssertionError() // The system has no TLS. Just give up.
    }

    return builder
      .connectTimeout(CONNECTION_TIMEOUT, SECONDS)
      .readTimeout(READ_TIMEOUT, SECONDS)
      .writeTimeout(WRITE_TIMEOUT, SECONDS)
      .cache(cache)
      .addInterceptor(apiInterceptor)
      .addInterceptor(logInterceptor)
      .build()
  }
}
