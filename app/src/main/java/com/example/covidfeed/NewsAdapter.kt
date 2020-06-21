package com.example.covidfeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.news_list.view.*

class NewsAdapter(private var items: List<News>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.news_list, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is NewsViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    class NewsViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val mImage = itemView.ivNews
        private val mTitle = itemView.tvTitle
        private val mAuthor = itemView.tvAuthor
        private val mDescription = itemView.tvDescription
        fun bind(obNews: News) {
            mTitle.text = obNews.title
            mAuthor.text = obNews.author
            mDescription.text = obNews.description
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(obNews.imageURL)
                .apply(RequestOptions().fitCenter())
                .into(mImage)
        }
    }
}
