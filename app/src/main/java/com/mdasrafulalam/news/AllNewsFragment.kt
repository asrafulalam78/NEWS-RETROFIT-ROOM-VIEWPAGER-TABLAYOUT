package com.mdasrafulalam.news

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mdasrafulal.NewsViewmodel
import com.mdasrafulalam.news.adapter.MainAdapter
import com.mdasrafulalam.news.adapter.MainLoadStateAdapter
import com.mdasrafulalam.news.adapter.NewsRecyclerViewAdapter
import com.mdasrafulalam.news.databinding.FragmentAllNewsBinding
import com.mdasrafulalam.news.model.News
import com.mdasrafulalam.news.utils.Constants
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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
//        binding.newsRV.adapter = adapter.withLoadStateFooter(
//            MainLoadStateAdapter()
//        )
//
//        lifecycleScope.launch {
//            viewModel.data.collectLatest {
//                adapter.submitData(it)
//                adapter.refresh()
//                Log.d("list", "list: $it")
//            }
//        }

        viewModel.getAllNews(Constants.COUNTRY.value.toString()).observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        swipeRefreshLayout.setOnRefreshListener{
            swipeRefreshLayout.isRefreshing = false
            if (!Constants.verifyAvailableNetwork(requireContext())){
                CookieBar.build(requireActivity())
                    .setTitle(getString(R.string.network_conncetion))
                    .setBackgroundColor(R.color.swipe_color_4)
                    .setTitleColor(R.color.white)
                    .setSwipeToDismiss(true)
                    .setMessage("No Active Internet!")
                    .setDuration(5000)
                    .setAnimationIn(android.R.anim.slide_in_left, android.R.anim.slide_in_left)
                    .setAnimationOut(android.R.anim.slide_out_right, android.R.anim.slide_out_right)
                    .show()
            }else{
                viewModel.refreshAllNews()
                viewModel.getAllNews(Constants.COUNTRY.value.toString()).observe(viewLifecycleOwner){
                    adapter.submitList(it)
                }
                CookieBar.build(requireActivity())
                    .setMessage("News Updated!")
                    .setDuration(5000)
                    .setBackgroundColor(R.color.color_tab_text)
                    .setIcon(R.drawable.success)
                    .setMessageColor(R.color.white)
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
                searchAction(query!!)
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                searchAction(newText!!)
                return true
            }
        })

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_search -> Toast.makeText(requireContext(),"Search", Toast.LENGTH_SHORT).show()
            R.id.action_voice -> displaySpeechRecognizerForDesc()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun displaySpeechRecognizerForDesc() {
        //Starts an activity that will prompt the user for speech and send it through a speech recognizer.
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, //Informs the recognizer which speech model to prefer when performing ACTION_RECOGNIZE_SPEECH.
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM //Use a language model based on free-form speech recognition.
            )
        }
        // This starts the activity and populates the intent with the speech text.
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            val spokenText =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                    results?.get(0) ?: return
                }
            Log.d("voice", spokenText)
            //setting voice text into input field
            searchAction(spokenText)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun searchAction(query:String){
        var newsList: List<News>
        viewModel.getAllNews(Constants.COUNTRY.value.toString()).observe(viewLifecycleOwner, Observer {
            newsList = it
            val collectionSearch: List<News> = newsList.filter {
                it.title!!.uppercase().contains(query.toString().uppercase())
            }.toList()
            adapter.submitList(collectionSearch)
        })

    }

}