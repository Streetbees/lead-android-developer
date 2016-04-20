package eu.oora.marvel.navigation.screen

import eu.oora.marvel.R
import eu.oora.marvel.dependency.Injector
import eu.oora.marvel.dependency.component.ActivityComponent
import eu.oora.marvel.dependency.scope.PerScreenScope
import eu.oora.marvel.navigation.Screen
import eu.oora.marvel.presenter.presenter.SplashScreenPresenter
import eu.oora.marvel.view.view.SplashScreenView

class SplashScreen : Screen {
  override val layout: Int = R.layout.screen_splash
  override val name: String = "Splash Screen"

  // will be created with init call from flow service
  lateinit var component: Component

  override fun init(parent: ActivityComponent): Component {
    component = DaggerSplashScreen_Component.builder()
      .activityComponent(parent)
      .build()

    return component
  }

  @PerScreenScope
  @dagger.Component(
    dependencies = arrayOf(
      ActivityComponent::class
    )
  )
  interface Component : ActivityComponent, Injector<SplashScreenView> {
    fun provideSplashScreenPresenter(): SplashScreenPresenter
  }
}
