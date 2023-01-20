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
import com.mdasrafulalam.news.databinding.FragmentHealthBinding
import com.mdasrafulalam.news.databinding.FragmentTechnologyBinding
import com.mdasrafulalam.news.model.News
import com.mdasrafulalam.news.utils.Constants
import org.aviran.cookiebar2.CookieBar

class TechnologyFragment : Fragment() {

    private lateinit var binding: FragmentTechnologyBinding
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
        binding = FragmentTechnologyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         adapter = NewsRecyclerViewAdapter(::updateBookmark)
        binding.technologyNewsRV.layoutManager = LinearLayoutManager(requireContext())
        binding.technologyNewsRV.adapter = adapter
        viewModel.getTechnologyNews(Constants.CATEGORY_TECHNOLOGY).observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        binding.technologySwipRefreshLayout.setOnRefreshListener{
            binding.technologySwipRefreshLayout.isRefreshing = false
            if (!Constants.verifyAvailableNetwork(requireContext())){
                CookieBar.build(requireActivity())
                    .setTitle("Network Connection")
                    .setMessage("No Active Internet!")
                    .setDuration(5000)
                    .setTitleColor(R.color.swipe_color_2)
                    .setBackgroundColor(R.color.swipe_color_4)
                    .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                    .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                    .show()
            }else{
                viewModel.refreshTechnologyNews()
                viewModel.getTechnologyNews(Constants.CATEGORY_TECHNOLOGY).observe(viewLifecycleOwner){
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
                viewModel.getTechnologyNews(Constants.CATEGORY_TECHNOLOGY).observe(viewLifecycleOwner, Observer {
                    newsList = it
                    val collectionSearch: List<News> = newsList.filter {
                        it.title!!.uppercase().contains(query.toString().uppercase())
                    }.toList()
                    adapter.submitList(collectionSearch)
                })

                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                var newsList: List<News>
                viewModel.getTechnologyNews(Constants.CATEGORY_TECHNOLOGY).observe(viewLifecycleOwner, Observer {
                    newsList = it
                    val collectionSearch: List<News> = newsList.filter {
                        it.title!!.uppercase().contains(newText.toString().uppercase())
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