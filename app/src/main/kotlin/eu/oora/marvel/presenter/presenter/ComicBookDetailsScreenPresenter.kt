package eu.oora.marvel.presenter.presenter

import eu.oora.marvel.model.model.ComicBookModel
import eu.oora.marvel.presenter.ViewPresenter
import eu.oora.marvel.view.holder.ComicBookDetailsScreenViewHolder

class ComicBookDetailsScreenPresenter(private val mComicBookModel: ComicBookModel) : ViewPresenter<ComicBookDetailsScreenViewHolder> {
  private var mViewHolder: ComicBookDetailsScreenViewHolder? = null

  override fun onTakeView(view: ComicBookDetailsScreenViewHolder) {
    mViewHolder = view
    mViewHolder?.setData(mComicBookModel)
  }

  override fun onDropView() {
    mViewHolder = null
  }
}
