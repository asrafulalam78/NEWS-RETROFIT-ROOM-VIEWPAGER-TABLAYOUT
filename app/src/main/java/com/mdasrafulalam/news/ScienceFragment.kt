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
import com.mdasrafulalam.news.databinding.FragmentScienceBinding
import com.mdasrafulalam.news.utils.Constants

class ScienceFragment : Fragment() {

    private lateinit var binding: FragmentScienceBinding
    private val viewModel: NewsViewmodel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScienceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = NewsRecyclerViewAdapter()
        binding.scienceNewsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.scienceNewsRV.adapter = adapter
        viewModel.refreshRV()
        viewModel.getScienceNews(Constants.CATEGORY_SCIENCE).observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        binding.scienceSwipRefreshLayout.setOnRefreshListener{
            binding.scienceSwipRefreshLayout.isRefreshing = false
            viewModel.refreshRV()
            viewModel.getScienceNews(Constants.CATEGORY_SCIENCE).observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
        }
    }
}