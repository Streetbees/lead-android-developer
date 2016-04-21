package eu.oora.marvel.view.view

import android.content.Context
import android.content.DialogInterface
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.widget.LinearLayout
import eu.oora.marvel.R
import eu.oora.marvel.dependency.Injector
import eu.oora.marvel.extenstion.bindView
import eu.oora.marvel.model.model.ComicBookModel
import eu.oora.marvel.navigation.ServicePool
import eu.oora.marvel.navigation.screen.ComicBookDetailsScreen
import eu.oora.marvel.presenter.presenter.ComicBookListScreenPresenter
import eu.oora.marvel.view.adapter.ComicBookAdapter
import eu.oora.marvel.view.holder.ComicBookListScreenViewHolder
import flow.Flow
import javax.inject.Inject

class ComicBookListScreenView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs), ComicBookListScreenViewHolder {
  private val mContentView: RecyclerView by bindView(R.id.comic_book_list_content)
  private val mRefreshLayout: SwipeRefreshLayout by bindView(R.id.comic_book_list_refresh_layout)

  private val mComicBookAdapter: ComicBookAdapter

  @Inject
  lateinit var presenter: ComicBookListScreenPresenter

  init {
    val component: Injector<ComicBookListScreenView>? = Flow.getService(ServicePool.INJECT_COMPONENT, context)
    component?.inject(this)

    val onLoadModeData = { presenter.loadMore() }
    val onModelClicked: (ComicBookModel) -> Unit = { model -> Flow.get(context).set(ComicBookDetailsScreen(model)) }

    mComicBookAdapter = ComicBookAdapter(context, onLoadModeData, onModelClicked)
  }

  override fun onFinishInflate() {
    super.onFinishInflate()

    // Calculate max span count
    val width = resources.getDimension(R.dimen.book_list_entry_cover_width)
    val density = resources.displayMetrics.density
    val max = resources.configuration.screenWidthDp

    val spanCount = (max / width * density).toInt()

    mContentView.layoutManager = GridLayoutManager(context, spanCount)
    mContentView.setHasFixedSize(true)
    mContentView.adapter = mComicBookAdapter

    mRefreshLayout.setOnRefreshListener { presenter.onRefresh() }
  }

  override fun setValues(values: List<ComicBookModel>) {
    mComicBookAdapter.values = values
  }

  override fun showProgressDialog() {
    mRefreshLayout.isRefreshing = true
  }

  override fun hideProgressDialog() {
    mRefreshLayout.isRefreshing = false
  }

  override fun showLoadingError() {
    AlertDialog.Builder(context)
      .setMessage(R.string.comic_book_list_error)
      .setPositiveButton(R.string.comic_book_list_error_retry, { dialogInterface: DialogInterface, i: Int -> presenter.onRefresh() })
      .create()
      .show()
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    presenter.onTakeView(this)
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    presenter.onDropView()
  }
}