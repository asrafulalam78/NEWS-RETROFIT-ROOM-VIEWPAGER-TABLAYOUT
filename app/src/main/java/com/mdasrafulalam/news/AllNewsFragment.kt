package com.mdasrafulalam.news

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mdasrafulal.NewsViewmodel
import com.mdasrafulalam.news.adapter.NewsRecyclerViewAdapter
import com.mdasrafulalam.news.databinding.FragmentAllNewsBinding
import com.mdasrafulalam.news.databinding.FragmentHomeBinding
import com.mdasrafulalam.news.model.News
import kotlinx.android.synthetic.main.fragment_all_news.*

class AllNewsFragment : Fragment() {
    private lateinit var binding: FragmentAllNewsBinding
    private  lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private val viewModel: NewsViewmodel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllNewsBinding.inflate(layoutInflater, container, false)
        swipeRefreshLayout = binding.refreshLayout
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = NewsRecyclerViewAdapter(::updateBookmark)
        binding.newsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.newsRV.adapter = adapter
        viewModel.getAllNews().observe(viewLifecycleOwner){
            adapter.submitList(it)
            Log.d("list", "list: $it")
        }
        swipeRefreshLayout.setOnRefreshListener{
            swipeRefreshLayout.isRefreshing = false
            viewModel.refreshRV()
            viewModel.getAllNews().observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
        }
    }
    private fun verifyAvailableNetwork(context: Context):Boolean{
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }
    fun updateBookmark(news: News) {
        viewModel.updateBookMark(news)
    }

}