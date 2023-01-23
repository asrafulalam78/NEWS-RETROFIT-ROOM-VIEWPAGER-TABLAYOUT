package com.mdasrafulalam.news

import android.R
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.coroutineScope
import com.mdasrafulal.NewsViewmodel
import com.mdasrafulalam.news.databinding.FragmentSettingsBinding
import com.mdasrafulalam.news.preference.DataPreference
import com.mdasrafulalam.news.utils.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.aviran.cookiebar2.CookieBar

class SettingsFragment : Fragment() {
    private lateinit var _binding: FragmentSettingsBinding
    private val viewModel: NewsViewmodel by activityViewModels()
    private lateinit var preferences: DataPreference
    private val binding get() = _binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        preferences = DataPreference(requireContext())

        Constants.DARKMODE.observe(viewLifecycleOwner, Observer {
                   binding.themeSwitch.isChecked = it
        })

        val countryAdapter = ArrayAdapter<String>(
            requireActivity(),
            R.layout.simple_spinner_dropdown_item,
            Constants.countryList
        )
        binding.themeSwitch.setOnCheckedChangeListener{_,isChecked->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.settingsFragmentLL.setBackgroundColor(resources.getColor(R.color.darker_gray))
                binding.themeSwitch.setBackgroundColor(resources.getColor(com.mdasrafulalam.news.R.color.nav_bar_start))
                binding.themeSwitch.text = getString(com.mdasrafulalam.news.R.string.disable_dark_mode)
//                lifecycle.coroutineScope.launch {
//                    preferences.setDarkModeValue(true, requireContext())
//                }
                Constants.DARKMODE.value = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.settingsFragmentLL.setBackgroundColor(resources.getColor(R.color.white))
                binding.themeSwitch.text = getString(com.mdasrafulalam.news.R.string.enable_dark_mode)
//                lifecycle.coroutineScope.launch {
//                    preferences.setDarkModeValue(false, requireContext())
//                }
                Constants.DARKMODE.value = false
            }
        }


        binding.countrySpinner.adapter = countryAdapter
        preferences.selectedPositionFlow.asLiveData().observe(requireActivity()) {
            binding.countrySpinner.setSelection(it.toInt())
        }
//        binding.countrySpinner.setSelection(24)
        binding.countrySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Constants.COUNTRY.value = Constants.countryCode[position]
                    lifecycle.coroutineScope.launch {
                        preferences.setCountry(Constants.COUNTRY.value.toString(), position.toLong(), requireContext())
                    }
                    Log.d("country", "Selected Country: ${Constants.COUNTRY.value.toString()}")
                    viewModel.refreshData()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
    }
}