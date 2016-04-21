package eu.oora.marvel.navigation.screen

import dagger.Module
import dagger.Provides
import eu.oora.marvel.R
import eu.oora.marvel.dependency.Injector
import eu.oora.marvel.dependency.component.ActivityComponent
import eu.oora.marvel.dependency.scope.PerScreenScope
import eu.oora.marvel.model.ComicBookService
import eu.oora.marvel.navigation.Screen
import eu.oora.marvel.presenter.presenter.ComicBookListScreenPresenter
import eu.oora.marvel.view.view.ComicBookListScreenView

class ComicBookListScreen : Screen {
  override val layout: Int = R.layout.screen_comic_book_list
  override val name: String = "Comic Book List Screen"

  // will be created with init call from flow service
  var component: ScreenComponent? = null
    private set

  override fun inject(parent: ActivityComponent): ScreenComponent {
    if (component == null) {
      component = DaggerComicBookListScreen_ScreenComponent.builder()
        .activityComponent(parent)
        .screenModule(ScreenModule())
        .build()
    }

    return component!!
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
  interface ScreenComponent : ActivityComponent, Injector<ComicBookListScreenView>

  @Module
  class ScreenModule {
    @Provides
    @PerScreenScope
    fun provideComicBookListScreenPresenter(service: ComicBookService): ComicBookListScreenPresenter {
      return ComicBookListScreenPresenter(service)
    }
  }
}
