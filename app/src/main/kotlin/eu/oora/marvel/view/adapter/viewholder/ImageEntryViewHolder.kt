package eu.oora.marvel.view.adapter.viewholder

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import eu.oora.marvel.R
import eu.oora.marvel.extenstion.bindView

class ImageEntryViewHolder(view: View) : ViewHolder(view) {
  private val mImageView: ImageView by bindView(R.id.image_entry)

  fun bind(data: String) {
    Picasso.with(mImageView.context)
      .load(data)
      .into(mImageView)
  }
}