package com.example.iconfinder.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.iconfinder.databinding.CategorylistItemBinding
import com.example.iconfinder.models.Category

class HomeAdapter : ListAdapter<Category, HomeAdapter.HomeViewHolder> (
    object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.toString() == newItem.toString()
        }
    }
)
{
    inner class HomeViewHolder(private val binding:CategorylistItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.category.text=category.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding=CategorylistItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}