package eu.oora.marvel.presenter.presenter

import eu.oora.marvel.model.ComicBookService
import eu.oora.marvel.presenter.ViewPresenter
import eu.oora.marvel.view.holder.SplashScreenViewHolder
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber

class SplashScreenPresenter(private val mCoverService: ComicBookService) : ViewPresenter<SplashScreenViewHolder> {
  private var mSplashScreenView: SplashScreenViewHolder? = null

  init {
    mCoverService.getComicBookList()
      .subscribeOn(Schedulers.newThread())
      .observeOn(AndroidSchedulers.mainThread())
      .doOnNext {
        Timber.d("OnNext: %s", it)
      }
      .subscribe()
  }

  override fun onTakeView(view: SplashScreenViewHolder) {
    mSplashScreenView = view
  }

  override fun onDropView() {
    mSplashScreenView = null
  }
}