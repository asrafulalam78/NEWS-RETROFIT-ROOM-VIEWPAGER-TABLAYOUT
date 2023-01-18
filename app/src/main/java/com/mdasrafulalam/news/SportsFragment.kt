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
import com.mdasrafulalam.news.databinding.FragmentSportsBinding
import com.mdasrafulalam.news.utils.Constants

class SportsFragment : Fragment() {

    private lateinit var binding: FragmentSportsBinding
    private val viewModel: NewsViewmodel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSportsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = NewsRecyclerViewAdapter()
        binding.sportsNewsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.sportsNewsRV.adapter = adapter
        viewModel.refreshRV()
        viewModel.getSportsNews(Constants.CATEGORY_SPORTS).observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        binding.sportsSwipRefreshLayout.setOnRefreshListener{
            binding.sportsSwipRefreshLayout.isRefreshing = false
            viewModel.refreshRV()
            viewModel.getSportsNews(Constants.CATEGORY_SPORTS).observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
        }
    }
}