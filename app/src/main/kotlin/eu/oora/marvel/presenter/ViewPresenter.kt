package eu.oora.marvel.presenter

interface ViewPresenter<T> {
  fun onTakeView(view: T)
  fun onDropView()
}