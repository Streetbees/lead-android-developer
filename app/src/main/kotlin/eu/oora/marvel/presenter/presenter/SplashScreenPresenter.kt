package eu.oora.marvel.presenter.presenter

import eu.oora.marvel.dependency.scope.PerScreenScope
import eu.oora.marvel.presenter.ViewPresenter
import eu.oora.marvel.view.holder.SplashScreenViewHolder
import javax.inject.Inject

@PerScreenScope
class SplashScreenPresenter : ViewPresenter<SplashScreenViewHolder> {
  private var mSplashScreenView: SplashScreenViewHolder? = null

  @Inject
  constructor()

  override fun onTakeView(view: SplashScreenViewHolder) {
    mSplashScreenView = view
  }

  override fun onDropView() {
    mSplashScreenView = null
  }
}