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
import com.mdasrafulalam.news.databinding.FragmentEntertainmentBinding
import com.mdasrafulalam.news.model.Article
import com.mdasrafulalam.news.model.News
import com.mdasrafulalam.news.utils.Constants


class EntertainmentFragment : Fragment() {
    private lateinit var binding: FragmentEntertainmentBinding
    private val viewModel: NewsViewmodel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEntertainmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = NewsRecyclerViewAdapter()
        binding.entertainmentNewsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.entertainmentNewsRV.adapter = adapter
        viewModel.refreshRV()
        viewModel.getBusinessNews(Constants.CATEGORY_ENTERTAINMENT).observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        binding.entertainmentFragmentSwipRefreshLayout.setOnRefreshListener{
            binding.entertainmentFragmentSwipRefreshLayout.isRefreshing = false
            viewModel.refreshRV()
            viewModel.getEntertainmentNews(Constants.CATEGORY_ENTERTAINMENT).observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
        }
    }


}