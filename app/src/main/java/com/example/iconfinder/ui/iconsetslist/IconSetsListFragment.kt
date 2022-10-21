package com.example.iconfinder.ui.iconsetslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.iconfinder.databinding.FragmentHomeBinding
import com.example.iconfinder.databinding.FragmentIconSetsListBinding
import com.example.iconfinder.ui.icons.IconsViewModel
import com.example.iconfinder.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class IconSetsListFragment : Fragment() {
    private var _binding: FragmentIconSetsListBinding? = null
    private val binding get() = _binding!!
    private val vieModel:IconSetsViewModel by viewModels()
    private lateinit var iconSetsAdapter: IconSetsAdapter
    private val args:IconSetsListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentIconSetsListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val identifier=args.identifier
        vieModel.getIconCategorySets(identifier)
        initObservers()
    }

    private fun initObservers() {
        vieModel.iconSetsResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    binding.progressBar.visibility=View.GONE
                    iconSetsAdapter= IconSetsAdapter(){iconSetId->
                        val direction=IconSetsListFragmentDirections.actionIconSetsListFragmentToIconsFragment(iconSetId)
                        findNavController().navigate(direction)
                    }
                    binding.rvIconSets.adapter=iconSetsAdapter
                    iconSetsAdapter.submitList(it.data?.iconsets)
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
        _binding=null
    }
}