package com.mdasrafulalam.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdasrafulal.NewsViewmodel
import com.mdasrafulalam.news.adapter.NewsRecyclerViewAdapter
import com.mdasrafulalam.news.databinding.FragmentBusinessBinding
import com.mdasrafulalam.news.databinding.FragmentHealthBinding
import com.mdasrafulalam.news.model.News
import com.mdasrafulalam.news.utils.Constants


class HealthFragment : Fragment() {
    private lateinit var binding: FragmentHealthBinding
    private val viewModel: NewsViewmodel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHealthBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = NewsRecyclerViewAdapter(::updateBookmark)
        binding.healthNewsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.healthNewsRV.adapter = adapter
        viewModel.refreshRV()
        viewModel.getHealthNews(Constants.CATEGORY_HEALTH).observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        binding.healthSwipRefreshLayout.setOnRefreshListener{
            binding.healthSwipRefreshLayout.isRefreshing = false
            viewModel.refreshRV()
            viewModel.getHealthNews(Constants.CATEGORY_HEALTH).observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
        }
    }
    fun updateBookmark(news: News) {
        viewModel.updateBookMark(news)
    }
}