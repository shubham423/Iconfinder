package com.example.iconfinder.ui.icons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.iconfinder.R
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
           if (icon.isPremium){
               binding.pricingImg.setImageResource(R.drawable.premium_icon)
           }else{
               binding.downloadIcon.visibility=View.VISIBLE
               binding.pricingImg.setImageResource(R.drawable.free_icon)
           }
            binding.nameTv.text=icon.tags[0]
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