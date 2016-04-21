package eu.oora.marvel.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import eu.oora.marvel.R
import eu.oora.marvel.model.model.ComicBookModel
import eu.oora.marvel.view.adapter.viewholder.ComicBookViewHolder

class ComicBookAdapter(private val mContext: Context, private val mOnLoadMoreResults: () -> Unit, private val mOnClickListener: (ComicBookModel) -> Unit) : RecyclerView.Adapter<ComicBookViewHolder>() {
  private val mInflater: LayoutInflater by lazy { LayoutInflater.from(mContext) }

  var values: List<ComicBookModel> = emptyList()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun onBindViewHolder(holder: ComicBookViewHolder?, position: Int) {
    holder?.let {
      it.bind(values[position])
    }

    if (itemCount > 0 && position > itemCount / 2) {
      mOnLoadMoreResults.invoke()
    }
  }

  override fun getItemCount(): Int {
    return values.size
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ComicBookViewHolder? {
    val view = mInflater.inflate(R.layout.view_comic_book_entry, parent, false)

    return ComicBookViewHolder(view, mOnClickListener)
  }
}