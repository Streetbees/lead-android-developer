package eu.oora.marvel.view.adapter.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.squareup.picasso.Picasso
import eu.oora.marvel.R
import eu.oora.marvel.extenstion.bindView
import eu.oora.marvel.model.model.ComicBookModel

class ComicBookViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  private val mCoverView: ImageView by bindView(R.id.comic_book_entry_cover)

  init {
    view.setOnClickListener {
      Toast.makeText(view.context, "Clicked!", Toast.LENGTH_SHORT).show()
    }
  }

  fun bind(model: ComicBookModel) {
    Picasso.with(mCoverView.context)
      .load(model.thumbnail)
      .into(mCoverView)
  }
}