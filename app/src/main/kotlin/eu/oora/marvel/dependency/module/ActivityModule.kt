package eu.oora.marvel.dependency.module

import android.content.Context
import dagger.Module
import dagger.Provides
import eu.oora.marvel.MainActivity
import eu.oora.marvel.dependency.scope.PerActivityScope
import eu.oora.marvel.view.MainLayout

@Module
class ActivityModule(private val activity: MainActivity) {
  @Provides
  @PerActivityScope
  fun provideBaseActivity(): MainActivity {
    return activity
  }

  @Provides
  @PerActivityScope
  fun provideContext(): Context {
    return activity
  }

  @Provides
  @PerActivityScope
  fun provideMainLayout(): MainLayout {
    return activity
  }
}
