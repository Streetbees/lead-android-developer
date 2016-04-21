package eu.oora.marvel.navigation.screen

import dagger.Module
import dagger.Provides
import eu.oora.marvel.R
import eu.oora.marvel.dependency.Injector
import eu.oora.marvel.dependency.component.ActivityComponent
import eu.oora.marvel.dependency.scope.PerScreenScope
import eu.oora.marvel.model.model.ComicBookModel
import eu.oora.marvel.navigation.Screen
import eu.oora.marvel.presenter.presenter.ComicBookDetailsScreenPresenter
import eu.oora.marvel.view.view.ComicBookDetailsScreenView

class ComicBookDetailsScreen(private val mComicBookModel: ComicBookModel) : Screen {
  override val layout: Int = R.layout.screen_comic_book_details
  override val name: String = "Comic Book Details Screen"

  // will be created with init call from flow service
  var component: ScreenComponent? = null
    private set

  override fun inject(parent: ActivityComponent): ScreenComponent {
    if (component == null) {
      component = DaggerComicBookDetailsScreen_ScreenComponent.builder()
        .activityComponent(parent)
        .screenModule(ScreenModule(mComicBookModel))
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
  interface ScreenComponent : ActivityComponent, Injector<ComicBookDetailsScreenView>

  @Module
  class ScreenModule(private val mComicBookModel: ComicBookModel) {
    @Provides
    fun provideComicBookDetailsScreenPresenter(): ComicBookDetailsScreenPresenter {
      return ComicBookDetailsScreenPresenter(mComicBookModel)
    }
  }
}
