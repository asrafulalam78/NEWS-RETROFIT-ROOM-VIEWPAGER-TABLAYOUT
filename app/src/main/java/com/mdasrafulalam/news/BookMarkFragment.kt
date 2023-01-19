package com.mdasrafulalam.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdasrafulal.NewsViewmodel
import com.mdasrafulalam.news.adapter.NewsRecyclerViewAdapter
import com.mdasrafulalam.news.databinding.FragmentBookMarkBinding
import com.mdasrafulalam.news.databinding.FragmentTechnologyBinding
import com.mdasrafulalam.news.model.News
import com.mdasrafulalam.news.utils.Constants

class BookMarkFragment : Fragment() {
    private lateinit var binding: FragmentBookMarkBinding
    private val viewModel: NewsViewmodel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookMarkBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = NewsRecyclerViewAdapter(::updateBookmark)
        binding.bookmrkNewsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.bookmrkNewsRV.adapter = adapter
        viewModel.refreshRV()
        viewModel.getBookMaredNews().observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        binding.boomarkRefreshLayout.setOnRefreshListener{
            binding.boomarkRefreshLayout.isRefreshing = false
            viewModel.refreshRV()
            viewModel.getBookMaredNews().observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
        }
    }
    fun updateBookmark(news: News) {
        viewModel.updateBookMark(news)
    }
}