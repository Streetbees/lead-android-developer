package eu.oora.marvel.dependency.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.Moshi.Builder
import dagger.Module
import dagger.Provides
import eu.oora.marvel.dependency.scope.PerApplicationScope

@Module
class DataModule {
  @Provides
  @PerApplicationScope
  fun provideMoshi(): Moshi {
    return Builder().build()
  }
}
