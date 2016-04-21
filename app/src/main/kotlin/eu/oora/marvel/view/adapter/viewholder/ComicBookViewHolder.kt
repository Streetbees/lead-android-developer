package eu.oora.marvel.view.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import eu.oora.marvel.R
import eu.oora.marvel.extenstion.bindView
import eu.oora.marvel.model.model.ComicBookModel

class ComicBookViewHolder(view: View, private val mOnClickListener: (ComicBookModel) -> Unit) : RecyclerView.ViewHolder(view) {
  private val mCoverView: ImageView by bindView(R.id.comic_book_entry_cover)

  private var mModel: ComicBookModel? = null

  init {
    view.setOnClickListener {
      mModel?.let {
        mOnClickListener.invoke(it)
      }
    }
  }

  fun bind(model: ComicBookModel) {
    mModel = model
    
    Picasso.with(mCoverView.context)
      .load(model.thumbnail)
      .into(mCoverView)
  }
}