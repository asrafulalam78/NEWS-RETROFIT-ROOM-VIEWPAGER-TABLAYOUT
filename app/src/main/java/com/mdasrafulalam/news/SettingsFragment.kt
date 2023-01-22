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
import androidx.fragment.app.activityViewModels
import com.mdasrafulal.NewsViewmodel
import com.mdasrafulalam.news.databinding.FragmentSettingsBinding
import com.mdasrafulalam.news.utils.Constants
import org.aviran.cookiebar2.CookieBar

class SettingsFragment : Fragment() {
    private lateinit var _binding: FragmentSettingsBinding
    private val viewModel: NewsViewmodel by activityViewModels()
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
        val countryAdapter = ArrayAdapter<String>(
            requireActivity(),
            R.layout.simple_spinner_dropdown_item,
            Constants.countryList
        
        )
        binding.countrySpinner.adapter = countryAdapter
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
                    Log.d("country", "Selected Country: ${Constants.COUNTRY.value.toString()}")
                    viewModel.refreshData()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
    }
}