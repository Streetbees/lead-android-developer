package eu.oora.marvel.dependency.component

import com.squareup.moshi.Moshi
import dagger.Component
import eu.oora.marvel.MainApplication
import eu.oora.marvel.dependency.module.ApiModule
import eu.oora.marvel.dependency.module.ApplicationModule
import eu.oora.marvel.dependency.module.DataModule
import eu.oora.marvel.dependency.module.NetworkModule
import eu.oora.marvel.dependency.scope.PerApplicationScope
import eu.oora.marvel.model.ComicBookService
import eu.oora.marvel.model.api.Api
import retrofit2.Retrofit

@PerApplicationScope
@Component(
  modules = arrayOf(
    ApplicationModule::class,
    ApiModule::class,
    DataModule::class,
    NetworkModule::class
  )
)
interface ApplicationComponent {
  fun inject(application: MainApplication)

  // Expose Retrofit
  fun provideRetrofit(): Retrofit

  // Expose Api
  fun provideApi(): Api

  // Expose Moshi
  fun provideMoshi(): Moshi

  // Expose ComicBookService
  fun provideComicBookService(): ComicBookService
}
