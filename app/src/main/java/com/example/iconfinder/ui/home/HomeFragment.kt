package com.example.iconfinder.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.iconfinder.R
import com.example.iconfinder.databinding.FragmentHomeBinding
import com.example.iconfinder.utils.Resource
import com.example.iconfinder.viewmodels.IconFinderViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: IconFinderViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getIconCategorySets()
        initObservers()
    }

    private fun initObservers() {
        viewModel.iconCategorySetResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    homeAdapter= HomeAdapter()
                    binding.rvCategoryList.adapter=homeAdapter
                    homeAdapter.submitList(it.data?.categories)
                }
                is Resource.Error -> {

                }
                else -> {

                }
            }
        }
    }
}