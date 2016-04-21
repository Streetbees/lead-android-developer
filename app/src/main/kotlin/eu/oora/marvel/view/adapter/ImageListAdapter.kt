package eu.oora.marvel.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import eu.oora.marvel.R
import eu.oora.marvel.view.adapter.viewholder.ImageEntryViewHolder

class ImageListAdapter(val context: Context) : Adapter<ImageEntryViewHolder>() {
  private val mInflater: LayoutInflater by lazy { LayoutInflater.from(context) }

  var values: List<String> = emptyList()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ImageEntryViewHolder? {
    val view = mInflater.inflate(R.layout.view_image_entry, parent, false)

    return ImageEntryViewHolder(view)
  }

  override fun onBindViewHolder(holder: ImageEntryViewHolder?, position: Int) {
    holder?.let {
      it.bind(values[position])
    }
  }

  override fun getItemCount(): Int {
    return values.size
  }
}