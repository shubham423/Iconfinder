package com.example.iconfinder.ui.iconsetslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iconfinder.R
import com.example.iconfinder.databinding.ItemIconBinding
import com.example.iconfinder.databinding.ItemIconsetsBinding
import com.example.iconfinder.models.Icon
import com.example.iconfinder.models.Iconset

class IconSetsAdapter : ListAdapter<Iconset, IconSetsAdapter.HomeViewHolder> (
    object : DiffUtil.ItemCallback<Iconset>() {
        override fun areItemsTheSame(oldItem: Iconset, newItem: Iconset): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Iconset, newItem: Iconset): Boolean {
            return oldItem.toString() == newItem.toString()
        }
    }
)
{
    inner class HomeViewHolder(private val binding:ItemIconsetsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(iconSet:Iconset, position: Int) {
           if (iconSet.isPremium){
               binding.pricingImg.setImageResource(R.drawable.premium_icon)
           }else{
               binding.pricingImg.setImageResource(R.drawable.free_icon)
           }
            binding.authorNameTv.text="Author name: ${iconSet.author.name}"
            binding.countTv.text="Icons count: ${iconSet.iconsCount}"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding=ItemIconsetsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position),position)
    }

}