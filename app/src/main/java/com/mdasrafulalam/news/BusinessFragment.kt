package com.mdasrafulalam.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdasrafulal.NewsViewmodel
import com.mdasrafulalam.news.adapter.NewsRecyclerViewAdapter
import com.mdasrafulalam.news.databinding.FragmentAllNewsBinding
import com.mdasrafulalam.news.databinding.FragmentBusinessBinding
import com.mdasrafulalam.news.model.Article
import com.mdasrafulalam.news.model.News
import com.mdasrafulalam.news.utils.Constants

class BusinessFragment : Fragment() {
    private lateinit var binding: FragmentBusinessBinding
    private val viewModel: NewsViewmodel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBusinessBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = NewsRecyclerViewAdapter()
        binding.businessNewsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.businessNewsRV.adapter = adapter
        viewModel.refreshRV()
//        viewModel.businessNewsList.observe(viewLifecycleOwner){
//            adapter.submitList(convertArticleToNews(it))
//        }
        viewModel.getBusinessNews(Constants.CATEGORY_BUSINESS).observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        binding.businessFragmentSwipRefreshLayout.setOnRefreshListener{
            binding.businessFragmentSwipRefreshLayout.isRefreshing = false
            viewModel.refreshRV()
            viewModel.getBusinessNews(Constants.CATEGORY_BUSINESS).observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
        }
    }

}