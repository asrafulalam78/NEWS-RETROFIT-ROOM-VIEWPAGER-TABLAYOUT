package com.mdasrafulalam.news

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mdasrafulal.NewsViewmodel
import com.mdasrafulalam.news.databinding.ActivityMainBinding
import com.mdasrafulalam.news.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*


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

    private lateinit var viewModel: NewsViewmodel
    lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider(this)[NewsViewmodel::class.java]
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener{ _, nd: NavDestination, _ ->
            if (nd.id == R.id.newsDetailsFragment || nd.id==R.id.webViewFragment){
                navView.visibility = View.GONE
            }else{
                navView.visibility = View.VISIBLE
            }
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.homeFragment -> navController.navigate(R.id.homeFragment)
                R.id.bookMarkFragment -> navController.navigate(R.id.bookMarkFragment)
                else -> navController.navigate(R.id.settingsFragment)
            }
            true
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.INTERNET),
                0
            )
        } else {
            Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT)
                .show()
//            Timber.d("Permission Already Granted")
        }
    }

}
