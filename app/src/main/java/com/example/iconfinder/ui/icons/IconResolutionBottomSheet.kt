package com.example.iconfinder.ui.icons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.iconfinder.databinding.FragmentResolutionBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class IconResolutionBottomSheet() : BottomSheetDialogFragment() {
    private val iconsViewModel: IconsViewModel by activityViewModels()
    private lateinit var binding: FragmentResolutionBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResolutionBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val icon = iconsViewModel.selectedIcon
        Timber.d("bottomsheet icon value ${icon.iconId} and $icon")
        binding.rvQualities.adapter =IconResolutionAdapter(requireContext(),icon){position->
            lifecycleScope.launchWhenCreated {
                iconsViewModel.selectedIcon.let { iconsViewModel.download(it, position) }
                dismiss()
            }
        }
    }
}


