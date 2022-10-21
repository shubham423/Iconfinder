package com.example.iconfinder.ui.icons

import android.Manifest
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.iconfinder.R
import com.example.iconfinder.databinding.FragmentIconsBinding
import com.example.iconfinder.models.Icon
import com.example.iconfinder.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class IconsFragment : Fragment() {
    private lateinit var binding: FragmentIconsBinding
    private val args : IconsFragmentArgs by navArgs()
    private lateinit var searchView: SearchView
    private lateinit var item: MenuItem
    private val viewModel: IconsViewModel by activityViewModels()
    private var iconSetId:Int?=null
    private lateinit var iconAdapter: IconsAdapter
    private var writePermissionGranted = false
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

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
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                writePermissionGranted = it
            }
        setHasOptionsMenu(true)
        initObservers()
    }

    private fun initObservers() {
        viewModel.iconsResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    binding.progressBar.visibility=View.GONE
                    iconAdapter= IconsAdapter(){icon ->
                        Timber.d("fragment icon value ${icon?.iconId} and $icon")
                        viewModel.selectedIcon = icon
                            askPermission()
                            downloadImage(icon)
                        }

                    binding.rvIcons.adapter=iconAdapter
                    iconAdapter.submitList(it.data?.icons)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.serach_menu, menu)
        item = menu.findItem(R.id.action_search)
        searchView = item.actionView as SearchView
        if (iconSetId==-1) {
            item.expandActionView()
            searchView.setQuery("", false)
        }else{
            if (iconSetId!=null){
                viewModel.getIconsFromIconSets(iconSetId!!)
            }
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

    private fun downloadImage(icon: Icon) {
        val bottomSheet = IconResolutionBottomSheet()
        bottomSheet.show(parentFragmentManager,"bottomsheet")
    }

    private fun askPermission() {
        if (!writePermissionGranted) {
            permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }
}