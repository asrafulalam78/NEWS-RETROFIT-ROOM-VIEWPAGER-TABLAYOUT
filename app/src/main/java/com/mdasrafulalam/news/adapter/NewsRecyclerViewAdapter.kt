package com.mdasrafulalam.news.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mdasrafulalam.news.AllNewsFragmentDirections
import com.mdasrafulalam.news.HomeFragmentDirections
import com.mdasrafulalam.news.databinding.FragmentAllNewsBinding
import com.mdasrafulalam.news.databinding.NewsListItemBinding
import com.mdasrafulalam.news.model.Article
import com.mdasrafulalam.news.model.News

class NewsRecyclerViewAdapter() :
    ListAdapter<News, NewsRecyclerViewAdapter.NewsViewHolder>(NewsDiffCallback()) {
    class NewsViewHolder( private val binding: NewsListItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(newsModel: News){
            binding.newsItem = newsModel
            binding.moreCard.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToNewsDetailsFragment(newsItem = newsModel)
                it.findNavController().navigate(action)
            }
        }
    }

   class NewsDiffCallback : DiffUtil.ItemCallback<News>(){
       override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
           return oldItem.url == newItem.url
       }
       @SuppressLint("DiffUtilEquals")
       override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
           return oldItem == newItem
       }

   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsModel = getItem(position)
        holder.bind(newsModel)
    }
}