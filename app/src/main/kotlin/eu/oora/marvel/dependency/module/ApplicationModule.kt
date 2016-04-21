package eu.oora.marvel.dependency.module

import android.content.res.Resources
import dagger.Module
import dagger.Provides
import eu.oora.marvel.MainApplication
import eu.oora.marvel.dependency.scope.PerApplicationScope

@Module
class ApplicationModule(private val application: MainApplication) {
  @Provides
  @PerApplicationScope
  fun provideMainApplication(): MainApplication {
    return application
  }

  @Provides
  @PerApplicationScope
  fun provideResources(): Resources {
    return application.resources
  }
}
