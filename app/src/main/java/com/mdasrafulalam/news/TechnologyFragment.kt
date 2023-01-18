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
import com.mdasrafulalam.news.databinding.FragmentHealthBinding
import com.mdasrafulalam.news.databinding.FragmentTechnologyBinding
import com.mdasrafulalam.news.utils.Constants

class TechnologyFragment : Fragment() {

    private lateinit var binding: FragmentTechnologyBinding
    private val viewModel: NewsViewmodel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTechnologyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = NewsRecyclerViewAdapter()
        binding.technologyNewsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.technologyNewsRV.adapter = adapter
        viewModel.refreshRV()
        viewModel.getTechnologyNews(Constants.CATEGORY_TECHNOLOGY).observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        binding.technologySwipRefreshLayout.setOnRefreshListener{
            binding.technologySwipRefreshLayout.isRefreshing = false
            viewModel.refreshRV()
            viewModel.getTechnologyNews(Constants.CATEGORY_TECHNOLOGY).observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
        }
    }
}