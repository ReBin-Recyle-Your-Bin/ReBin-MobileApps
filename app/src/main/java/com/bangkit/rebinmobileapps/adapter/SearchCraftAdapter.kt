package com.bangkit.rebinmobileapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.data.model.Craft
import com.bangkit.rebinmobileapps.databinding.ItemCraftBinding

class SearchCraftAdapter : ListAdapter<Craft, SearchCraftAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCraftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val craft = getItem(position)
        holder.bind(craft)
    }
    class MyViewHolder(
        private val binding: ItemCraftBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(craft: Craft) {
            binding.tvTitle.text = craft.title
            binding.tvDescription.text = craft.description
            binding.ivImage.setImageResource(craft.imageResource)

        }

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Craft>() {
            override fun areItemsTheSame(oldItem: Craft, newItem: Craft): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Craft, newItem: Craft): Boolean {
                return oldItem == newItem
            }

        }
    }


}

















