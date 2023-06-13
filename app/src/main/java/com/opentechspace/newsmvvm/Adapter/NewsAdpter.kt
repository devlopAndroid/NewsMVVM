package com.opentechspace.newsmvvm.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.opentechspace.newsmvvm.Model.Article
import com.opentechspace.newsmvvm.R

class NewsAdapter(private val context: Context,val onItemClick :OnItemClickListener ) : RecyclerView.Adapter<NewsAdapter.MyViewHolder>(){

    inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        val image = itemView.findViewById<ImageView>(R.id.image)
        val title = itemView.findViewById<TextView>(R.id.title)
        val discription = itemView.findViewById<TextView>(R.id.discription)
        val readMore = itemView.findViewById<TextView>(R.id.readMore)
        val author = itemView.findViewById<TextView>(R.id.author)
        val publishedAt = itemView.findViewById<TextView>(R.id.publishedAt)
    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
          return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.customlayout,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val data = differ.currentList[position]
        Glide.with(context)
                .load(data.urlToImage)
                .into(holder.image)

        holder.title.text = data.title
        holder.discription.text = data.description
        holder.readMore.setOnClickListener {
            onItemClick.OnClick(data)
        }
        holder.author.text = data.source.name
        holder.publishedAt.text = data.publishedAt
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
}
interface OnItemClickListener{
    fun OnClick(article: Article)
}