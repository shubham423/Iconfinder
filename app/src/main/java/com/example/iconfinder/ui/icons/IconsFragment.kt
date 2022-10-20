package com.example.iconfinder.ui.icons

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.iconfinder.R
import com.example.iconfinder.databinding.FragmentIconsBinding
import com.example.iconfinder.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class IconsFragment : Fragment() {
    private lateinit var binding: FragmentIconsBinding
    private val args : IconsFragmentArgs by navArgs()
    private lateinit var searchView: SearchView
    private lateinit var item: MenuItem
    private val viewModel: IconsViewModel by viewModels()
    private var iconSetId:Int?=null
    private lateinit var iconAdapter: IconsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentIconsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iconSetId=args.iconSetId
        setHasOptionsMenu(true)
        initObservers()
    }

    private fun initObservers() {
        viewModel.iconsResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    iconAdapter= IconsAdapter()
                    binding.rvIcons.adapter=iconAdapter
                    iconAdapter.submitList(it.data?.icons)
                }
                is Resource.Error -> {

                }
                else -> {

                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.serach_menu, menu)
        item = menu.findItem(R.id.action_search)
        searchView = item.actionView as SearchView
        if (iconSetId==-1) {
            item.expandActionView()
            searchView.setQuery("", false)
        }


        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                val direction = IconsFragmentDirections.actionIconsFragmentToHomeFragment()
                findNavController().navigate(direction)
                return true
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null && query != "") {
                    viewModel.setQuery(query)
                    viewModel.searchIcons()
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

    }
}