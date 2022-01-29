package com.example.gfgnewsapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gfgnewsapp.R
import com.example.gfgnewsapp.model.Item
import com.example.gfgnewsapp.ui.NewsViewModel
import kotlinx.android.synthetic.main.layout_news.view.*

class NewsAdapter(
    val viewModel: NewsViewModel,
) : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(
            oldItem: Item,
            newItem: Item,
        ): Boolean {
            return oldItem.link == newItem.link
        }

        override fun areContentsTheSame(
            oldItem: Item,
            newItem: Item,
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_news,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(
        holder: ArticleViewHolder,
        position: Int,
    ) {
        val item = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(item.enclosure.thumbnail).into(image_news)
            text_title.text = item.title
            text_date.text = item.pubDate
//            setOnClickListener {
//                onItemClickListener?.let { it(article) }
//            }
        }
    }

    private var onItemClickListener: ((Item) -> Unit)? = null

    fun setOnItemClickListener(listener: (Item) -> Unit) {
        onItemClickListener = listener
    }
}