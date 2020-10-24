package com.phucduong.searchgitprofile.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.phucduong.searchgitprofile.databinding.SearchFragmentBinding
import com.phucduong.searchgitprofile.viewmodel.SearchUserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchWeatherFragment : Fragment() {
    private lateinit var viewBinding: SearchFragmentBinding
    private val viewModel by viewModels<SearchUserViewModel>()
    companion object {
        fun newInstance() = SearchWeatherFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = SearchFragmentBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()
    }

    private fun setupListAdapter() {
//        val weatherAdapter = WeatherAdapter(ArrayList(0))
//        viewBinding.weatherListInfo.adapter = weatherAdapter
    }
}
