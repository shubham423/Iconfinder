package com.example.iconfinder.ui.iconsetslist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.iconfinder.R
import com.example.iconfinder.databinding.FragmentIconSetsListBinding


class IconSetsListFragment : Fragment() {
    private lateinit var binding:FragmentIconSetsListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_icon_sets_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}