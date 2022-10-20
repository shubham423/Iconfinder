package com.example.iconfinder.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.iconfinder.MainActivity
import com.example.iconfinder.R
import com.example.iconfinder.databinding.FragmentHomeBinding
import com.example.iconfinder.utils.Resource
import com.example.iconfinder.viewmodels.IconFinderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: IconFinderViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var searchView: SearchView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity?)?.let {
            it.supportActionBar?.setDisplayShowHomeEnabled(true)
        }
        viewModel.getIconCategorySets()
        initObservers()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)
        val searchItem = menu.findItem(R.id.search_icon)
        searchView = searchItem.actionView as SearchView


        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                return true
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null && query != "") {
                    val directions=HomeFragmentDirections.actionHomeFragmentToIconsFragment(query)
                    findNavController().navigate(directions)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.search_icon){
            (activity as MainActivity?)?.let {
                it.supportActionBar?.title = ""
                it.supportActionBar?.setDisplayShowHomeEnabled(false)
            }

        }
        return super.onOptionsItemSelected(item)
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