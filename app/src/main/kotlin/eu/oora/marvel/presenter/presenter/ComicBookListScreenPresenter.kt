package eu.oora.marvel.presenter.presenter

import eu.oora.marvel.model.ComicBookService
import eu.oora.marvel.model.model.ComicBookModel
import eu.oora.marvel.presenter.ViewPresenter
import eu.oora.marvel.view.holder.ComicBookListScreenViewHolder
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.ArrayList

class ComicBookListScreenPresenter(private val mComicBookService: ComicBookService) : ViewPresenter<ComicBookListScreenViewHolder> {
  private var mViewHolder: ComicBookListScreenViewHolder? = null
  private val mComicBookList: ArrayList<ComicBookModel> = ArrayList()

  private var mPageNumber = 0
  private var mSubscription: Subscription? = null

  init {
    loadMore()
  }

  fun loadMore() {
    mSubscription?.let {
      if (!it.isUnsubscribed) {
        return
      }
    }

    mSubscription = mComicBookService.getComicBookList(mPageNumber)
      .subscribeOn(Schedulers.newThread())
      .observeOn(AndroidSchedulers.mainThread())
      .doOnNext {
        mComicBookList.add(it)
      }
      .doOnCompleted {
        mViewHolder?.setValues(mComicBookList)
        mPageNumber++
      }
      .subscribe()
  }

  override fun onTakeView(view: ComicBookListScreenViewHolder) {
    mViewHolder = view
    mViewHolder?.setValues(mComicBookList)
  }

  override fun onDropView() {
    mViewHolder = null
  }
}
