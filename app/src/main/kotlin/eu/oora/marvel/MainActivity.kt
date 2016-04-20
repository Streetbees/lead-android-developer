package eu.oora.marvel

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import eu.oora.marvel.extenstion.bindView
import eu.oora.marvel.navigation.NavigationScreenChanger
import eu.oora.marvel.navigation.screen.SplashScreen
import eu.oora.marvel.view.MainLayout
import flow.Flow
import flow.KeyDispatcher

class MainActivity : AppCompatActivity(), MainLayout {
  // Use Android application root view as main layout holder to reduce view hierarchy
  override val content by bindView<ViewGroup>(android.R.id.content)

  override fun attachBaseContext(newBase: Context?) {
    // Create navigation screen change handler
    val changer = NavigationScreenChanger(this)

    // Init Flow and overwrite attached context by wrapping it with Flow
    val context = Flow.configure(newBase, this)
      .dispatcher(KeyDispatcher.configure(this, changer).build())
      .defaultKey(SplashScreen())
      .install()

    super.attachBaseContext(context)
  }
}
