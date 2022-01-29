package com.example.gfgnewsapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gfgnewsapp.R
import com.example.gfgnewsapp.ReadNewsActivity
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
        val imageLink = item.enclosure.link
        /*
        removing 'amp;' from the image link string, so that image can be
        loaded into image view.
         */
        val newImageLink = imageLink.replace("amp;","")

        holder.itemView.apply {
            Glide.with(this).load(newImageLink).into(image_news)
            text_title.text = item.title
            text_date.text = item.pubDate
            holder.itemView.setOnClickListener {
                val dataIntent = Intent(
                    context,
                    ReadNewsActivity::class.java
                )
                dataIntent.putExtra("url",item.link)
                context.startActivity(dataIntent)
            }
        }
    }
}