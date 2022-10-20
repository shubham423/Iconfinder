package com.example.iconfinder.ui.icons

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iconfinder.databinding.ItemIconBinding
import com.example.iconfinder.models.Icon

class IconsAdapter : ListAdapter<Icon, IconsAdapter.HomeViewHolder> (
    object : DiffUtil.ItemCallback<Icon>() {
        override fun areItemsTheSame(oldItem: Icon, newItem: Icon): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Icon, newItem: Icon): Boolean {
            return oldItem.toString() == newItem.toString()
        }
    }
)
{
    inner class HomeViewHolder(private val binding:ItemIconBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(icon: Icon, position: Int) {
            binding.pricingTv.text=if (icon.isPremium) "Paid" else "Free"
         Glide.with(itemView)
             .load(icon.rasterSizes[icon.rasterSizes.lastIndex].formats[0].previewUrl)
             .into(binding.iconImg)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding=ItemIconBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position),position)
    }

}