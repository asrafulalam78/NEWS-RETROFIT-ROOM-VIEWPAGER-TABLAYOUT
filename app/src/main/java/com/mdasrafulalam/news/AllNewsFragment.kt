package com.mdasrafulalam.news

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mdasrafulal.NewsViewmodel
import com.mdasrafulalam.news.adapter.NewsRecyclerViewAdapter
import com.mdasrafulalam.news.databinding.FragmentAllNewsBinding
import com.mdasrafulalam.news.databinding.FragmentHomeBinding
import com.mdasrafulalam.news.model.News
import com.mdasrafulalam.news.utils.Constants
import kotlinx.android.synthetic.main.fragment_all_news.*
import org.aviran.cookiebar2.CookieBar

class AllNewsFragment : Fragment() {
    private lateinit var binding: FragmentAllNewsBinding
    private  lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: NewsRecyclerViewAdapter

    private val viewModel: NewsViewmodel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
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
        adapter = NewsRecyclerViewAdapter(::updateBookmark)
        binding.newsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.newsRV.adapter = adapter
        viewModel.getAllNews().observe(viewLifecycleOwner){
            adapter.submitList(it)
            Log.d("list", "list: $it")
        }
        swipeRefreshLayout.setOnRefreshListener{
            swipeRefreshLayout.isRefreshing = false
            if (!Constants.verifyAvailableNetwork(requireContext())){
                CookieBar.build(requireActivity())
                    .setTitle("Network Connection")
                    .setMessage("No Active Internet!")
                    .setTitleColor(R.color.swipe_color_3)
                    .setDuration(5000)
                    .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                    .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                    .show()
            }else{
                viewModel.refreshAllNews()
                viewModel.getAllNews().observe(viewLifecycleOwner){
                    adapter.submitList(it)
                }
                CookieBar.build(requireActivity())
                    .setMessage("News Updated!")
                    .setDuration(5000)
                    .setMessageColor(R.color.white)
                    .setBackgroundColor(R.color.swipe_color_1)
                    .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                    .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                    .show()
            }

        }
    }

    fun updateBookmark(news: News) {
        viewModel.updateBookMark(news)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu,menu)
        val search = menu?.findItem(R.id.action_search)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                var newsList: List<News>
                viewModel.getAllNews().observe(viewLifecycleOwner, Observer {
                    newsList = it
                    var collectionSearch: List<News> = newsList.filter {
                        it.title!!.toUpperCase().contains(query.toString().toUpperCase())
                    }.toList()
                    adapter.submitList(collectionSearch)
                })

                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                var newsList: List<News>
                viewModel.getAllNews().observe(viewLifecycleOwner, Observer {
                    newsList = it
                    var collectionSearch: List<News> = newsList.filter {
                        it.title!!.toUpperCase().contains(newText.toString().toUpperCase())
                    }.toList()
                    adapter.submitList(collectionSearch)
                })
                return true
            }
        })

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_search -> Toast.makeText(requireContext(),"Search", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

}