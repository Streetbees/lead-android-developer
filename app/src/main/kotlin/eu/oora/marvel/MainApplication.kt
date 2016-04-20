package eu.oora.marvel

import android.app.Application
import timber.log.Timber

class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()

    // Log output only in debug builds
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }
}