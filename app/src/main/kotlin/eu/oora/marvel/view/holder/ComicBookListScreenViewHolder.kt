package eu.oora.marvel.view.holder

import eu.oora.marvel.model.model.ComicBookModel

interface ComicBookListScreenViewHolder {
  fun setValues(values: List<ComicBookModel>)
  fun showProgressDialog()
  fun hideProgressDialog()
  fun showLoadingError()
}