package eu.oora.marvel

import android.app.Application
import eu.oora.marvel.dependency.component.ApplicationComponent
import eu.oora.marvel.dependency.component.DaggerApplicationComponent
import eu.oora.marvel.dependency.module.ApplicationModule
import timber.log.Timber

class MainApplication : Application() {
  val component: ApplicationComponent by lazy {
    DaggerApplicationComponent.builder()
      .applicationModule(ApplicationModule(this))
      .build()
  }

  override fun onCreate() {
    super.onCreate()

    component.inject(this)

    // Log output only in debug builds
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }
}