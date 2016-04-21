package eu.oora.marvel.view.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import eu.oora.marvel.R
import eu.oora.marvel.dependency.Injector
import eu.oora.marvel.extenstion.bindView
import eu.oora.marvel.model.model.ComicBookModel
import eu.oora.marvel.navigation.ServicePool
import eu.oora.marvel.presenter.presenter.ComicBookDetailsScreenPresenter
import eu.oora.marvel.view.adapter.ImageListAdapter
import eu.oora.marvel.view.holder.ComicBookDetailsScreenViewHolder
import flow.Flow
import javax.inject.Inject

class ComicBookDetailsScreenView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs), ComicBookDetailsScreenViewHolder {
  private val mActionBarTitleView: TextView by bindView(R.id.comic_book_details_actionbar_title)
  private val mActionBarBackView: View by bindView(R.id.comic_book_details_actionbar_back)
  private val mImagesView: RecyclerView by bindView(R.id.comic_book_details_images)
  private val mTitleView: TextView by bindView(R.id.comic_book_details_title)
  private val mDescriptionView: TextView by bindView(R.id.comic_book_details_description)
  private val mFormatView: TextView by bindView(R.id.comic_book_details_format)
  private val mPageCountView: TextView by bindView(R.id.comic_book_details_page_count)
  private val mIssueNumberView: TextView by bindView(R.id.comic_book_details_issue_number)

  private val mImageListAdapter: ImageListAdapter

  @Inject
  lateinit var presenter: ComicBookDetailsScreenPresenter

  init {
    val component: Injector<ComicBookDetailsScreenViewHolder>? = Flow.getService(ServicePool.INJECT_COMPONENT, context)
    component?.inject(this)

    mImageListAdapter = ImageListAdapter(context)
  }

  override fun onFinishInflate() {
    super.onFinishInflate()

    mImagesView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    mImagesView.setHasFixedSize(true)
    mImagesView.adapter = mImageListAdapter

    mActionBarBackView.setOnClickListener { Flow.get(context).goBack() }
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    presenter.onTakeView(this)
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    presenter.onDropView()
  }

  override fun setData(model: ComicBookModel) {
    mActionBarTitleView.text = model.title

    mImageListAdapter.values = model.images

    mTitleView.text = model.title
    mDescriptionView.text = model.description
    mFormatView.text = model.format
    mPageCountView.text = model.pageCount
    mIssueNumberView.text = model.issueNumber
  }
}
