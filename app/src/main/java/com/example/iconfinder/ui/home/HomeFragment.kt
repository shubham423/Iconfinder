package com.example.iconfinder.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.iconfinder.MainActivity
import com.example.iconfinder.R
import com.example.iconfinder.databinding.FragmentHomeBinding
import com.example.iconfinder.ui.iconsetslist.IconSetsViewModel
import com.example.iconfinder.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val iconSetViewModel: IconSetsViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentHomeBinding.inflate(layoutInflater)
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
        inflater.inflate(R.menu.serach_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.action_search){
            val action = HomeFragmentDirections.actionHomeFragmentToIconsFragment(-1)
            findNavController().navigate(action)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initObservers() {
        viewModel.iconCategorySetResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    binding.progressBar.visibility=View.GONE
                    homeAdapter= HomeAdapter(){identifier ->
                        iconSetViewModel.setIdentifier(identifier)
                        val direction=HomeFragmentDirections.actionHomeFragmentToIconSetsListFragment(identifier)
                        findNavController().navigate(direction)
                    }
                    binding.rvCategoryList.adapter=homeAdapter
                    homeAdapter.submitList(it.data?.categories)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility=View.GONE
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility=View.VISIBLE
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}