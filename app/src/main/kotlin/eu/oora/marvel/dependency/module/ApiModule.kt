package eu.oora.marvel.dependency.module

import android.content.res.Resources
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import eu.oora.marvel.MainApplication
import eu.oora.marvel.R
import eu.oora.marvel.dependency.scope.PerApplicationScope
import eu.oora.marvel.model.api.Api
import eu.oora.marvel.model.api.ApiInterceptor
import eu.oora.marvel.model.api.ApiProperties
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class ApiModule {
  @Provides
  @PerApplicationScope
  fun provideRetrofit(client: OkHttpClient, moshi: Moshi, resources: Resources): Retrofit {
    return Builder()
      .client(client)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
      .baseUrl(resources.getString(R.string.api_url))
      .build()
  }

  @Provides
  @PerApplicationScope
  fun provideApi(retrofit: Retrofit): Api {
    return retrofit.create(Api::class.java)
  }

  @Provides
  @PerApplicationScope
  fun provideApiProperties(application: MainApplication): ApiProperties {
    return ApiProperties(application)
  }

  @Provides
  @PerApplicationScope
  fun provideApiInterceptor(properties: ApiProperties): ApiInterceptor {
    return ApiInterceptor(properties)
  }
}
