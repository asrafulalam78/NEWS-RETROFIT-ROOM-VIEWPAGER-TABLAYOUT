package com.mdasrafulalam.news

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mdasrafulal.NewsViewmodel
import com.mdasrafulalam.news.adapter.NewsRecyclerViewAdapter
import com.mdasrafulalam.news.databinding.FragmentBusinessBinding
import com.mdasrafulalam.news.databinding.FragmentEntertainmentBinding
import com.mdasrafulalam.news.model.Article
import com.mdasrafulalam.news.model.News
import com.mdasrafulalam.news.utils.Constants
import org.aviran.cookiebar2.CookieBar


class EntertainmentFragment : Fragment() {
    private lateinit var binding: FragmentEntertainmentBinding
    private val viewModel: NewsViewmodel by activityViewModels()
    private lateinit var adapter: NewsRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEntertainmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = NewsRecyclerViewAdapter(::updateBookmark)
        binding.entertainmentNewsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.entertainmentNewsRV.adapter = adapter
        viewModel.getBusinessNews(Constants.CATEGORY_ENTERTAINMENT).observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        binding.entertainmentFragmentSwipRefreshLayout.setOnRefreshListener{
            binding.entertainmentFragmentSwipRefreshLayout.isRefreshing = false
            if (!Constants.verifyAvailableNetwork(requireContext())){
                CookieBar.build(requireActivity())
                    .setTitle("Network Connection")
                    .setTitleColor(R.color.swipe_color_4)
                    .setMessage("No Active Internet!")
                    .setDuration(5000)
                    .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                    .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                    .show()
            }else{
                viewModel.refreshEntertainmentNews()
                viewModel.getEntertainmentNews(Constants.CATEGORY_ENTERTAINMENT).observe(viewLifecycleOwner){
                    adapter.submitList(it)
                }
                CookieBar.build(requireActivity())
                    .setMessage("News Updated!")
                    .setDuration(5000)
                    .setBackgroundColor(R.color.swipe_color_1)
                    .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                    .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                    .show()
            }

        }
    }
    fun updateBookmark(news:News) {
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
                viewModel.getEntertainmentNews(Constants.CATEGORY_ENTERTAINMENT).observe(viewLifecycleOwner, Observer {
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
                viewModel.getEntertainmentNews(Constants.CATEGORY_ENTERTAINMENT).observe(viewLifecycleOwner, Observer {
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