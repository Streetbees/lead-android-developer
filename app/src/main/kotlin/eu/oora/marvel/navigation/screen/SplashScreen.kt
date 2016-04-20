package eu.oora.marvel.navigation.screen

import dagger.Module
import dagger.Provides
import eu.oora.marvel.R
import eu.oora.marvel.dependency.Injector
import eu.oora.marvel.dependency.component.ActivityComponent
import eu.oora.marvel.dependency.scope.PerScreenScope
import eu.oora.marvel.model.ComicBookService
import eu.oora.marvel.navigation.Screen
import eu.oora.marvel.presenter.presenter.SplashScreenPresenter
import eu.oora.marvel.view.view.SplashScreenView

class SplashScreen : Screen {
  override val layout: Int = R.layout.screen_splash
  override val name: String = "Splash Screen"

  // will be created with init call from flow service
  lateinit var component: ScreenComponent

  override fun init(parent: ActivityComponent): ScreenComponent {
    component = DaggerSplashScreen_ScreenComponent.builder()
      .activityComponent(parent)
      .build()

    return component
  }

  @PerScreenScope
  @dagger.Component(
    dependencies = arrayOf(
      ActivityComponent::class
    ),
    modules = arrayOf(
      ScreenModule::class
    )
  )
  interface ScreenComponent : ActivityComponent, Injector<SplashScreenView>

  @Module
  class ScreenModule {
    @Provides
    fun provideSplashScreenPresenter(service: ComicBookService): SplashScreenPresenter {
      return SplashScreenPresenter(service)
    }
  }
}
