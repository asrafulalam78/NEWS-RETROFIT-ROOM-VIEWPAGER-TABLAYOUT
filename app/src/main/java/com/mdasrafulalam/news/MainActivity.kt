package com.mdasrafulalam.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mdasrafulalam.news.databinding.ActivityMainBinding

val categoryArray = arrayOf(
    "All News",
    "Business",
    "Entertainment",
    "Science",
    "Sports",
    "Technology",
    "Health"
)
class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.homeFragment -> navController.navigate(R.id.homeFragment)
                R.id.bookMarkFragment -> navController.navigate(R.id.bookMarkFragment)
//                    loadFragment(BookmarkFragment())
                else -> navController.navigate(R.id.settingsFragment)
            }
            true
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

//
//        val viewPager = binding.viewPager
//        val tabLayout = binding.tabLayout
//        tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
//
//        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
//        viewPager.adapter = adapter
//
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = categoryArray[position]
//        }.attach()
    }
//private fun loadFragment(fragment: Fragment) {
//    val transaction = supportFragmentManager.beginTransaction()
//    transaction.replace(R.id.nav_host_fragment, fragment)
//    transaction.commit()
//}
