package com.example.iconfinder.ui.icons

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iconfinder.databinding.ItemDownloadBinding
import com.example.iconfinder.models.Icon
import com.example.iconfinder.models.RasterSize


class IconResolutionAdapter(val context: Context, val icon: Icon,private val callback:(Int)->Unit) :
    RecyclerView.Adapter<IconResolutionAdapter.DownloadViewHolder>() {
    private val list = icon.rasterSizes

    class DownloadViewHolder(private val binding:ItemDownloadBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RasterSize, callback: (Int) -> Unit) {
            val size = item.size.toString()
            val format = item.formats[0].format
            binding.tvSize.text = "Size: $size ✖ $size "
            binding.tvFormat.text = "Format: $format"
            binding.root.setOnClickListener {
               callback.invoke(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadViewHolder {
        val binding =
            ItemDownloadBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DownloadViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DownloadViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item,callback)


    }

    override fun getItemCount(): Int {
        return list.size

    }

    interface OnItemClicked {
        fun onItemClicked(position: Int)
    }

}