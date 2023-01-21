package com.mdasrafulalam.news

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.marginStart
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import com.google.android.material.tabs.TabLayoutMediator
import com.mdasrafulal.NewsViewmodel
import com.mdasrafulalam.news.databinding.FragmentHomeBinding
import com.mdasrafulalam.news.model.News
import com.mdasrafulalam.news.utils.Constants
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

     val viewModel: NewsViewmodel by activityViewModels()
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val tabLayout = binding.tabLayout
        val viewPage = binding.viewPager
        val tabAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        viewPage.adapter = tabAdapter
        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            tab.text = Constants.categoryArray[position]
            
        }.attach()
        tabLayout.setSelectedTabIndicatorColor(R.drawable.bg_shape)
    }
}