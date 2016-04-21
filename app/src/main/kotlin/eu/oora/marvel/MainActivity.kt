package eu.oora.marvel

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import eu.oora.marvel.dependency.ComponentProvider
import eu.oora.marvel.dependency.DependencyService
import eu.oora.marvel.dependency.component.ActivityComponent
import eu.oora.marvel.dependency.component.DaggerActivityComponent
import eu.oora.marvel.dependency.module.ActivityModule
import eu.oora.marvel.extenstion.bindView
import eu.oora.marvel.navigation.NavigationScreenChanger
import eu.oora.marvel.navigation.screen.ComicBookListScreen
import eu.oora.marvel.view.MainLayout
import flow.Flow
import flow.KeyDispatcher

class MainActivity : AppCompatActivity(), MainLayout, ComponentProvider<ActivityComponent> {
  // Activity based component for dependency injection
  override val component: ActivityComponent by lazy {
    DaggerActivityComponent.builder()
      .applicationComponent((application as MainApplication).component)
      .activityModule(ActivityModule(this))
      .build()
  }
  // Use Android application root view as main layout holder to reduce view hierarchy
  override val content by bindView<ViewGroup>(android.R.id.content)

  override fun attachBaseContext(newBase: Context?) {
    // Create navigation screen change handler
    val changer = NavigationScreenChanger(this)

    // Init Flow and overwrite attached context by wrapping it with Flow
    val context = Flow.configure(newBase, this)
      .dispatcher(KeyDispatcher.configure(this, changer).build())
      .defaultKey(ComicBookListScreen())
      .addServicesFactory(DependencyService(this))
      .install()

    super.attachBaseContext(context)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    component.inject(this)
  }
}
