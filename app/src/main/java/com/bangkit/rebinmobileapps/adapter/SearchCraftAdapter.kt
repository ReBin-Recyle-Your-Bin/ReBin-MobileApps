package com.bangkit.rebinmobileapps.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.data.response.SearchCraftItems
import com.bangkit.rebinmobileapps.databinding.ItemCraftBinding
import com.bangkit.rebinmobileapps.view.detail.DetailCraftActivity
import com.bumptech.glide.Glide

class SearchCraftAdapter : ListAdapter<SearchCraftItems, SearchCraftAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCraftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val craft = getItem(position)
        holder.bind(craft)
    }
    inner class MyViewHolder(
        private val binding: ItemCraftBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(craft: SearchCraftItems) {
            binding.tvTitle.text = craft.name
            val formattedClassName = craft.className.replace("-", " ")
            binding.tvClass.text = formattedClassName
            binding.tvDescription.text = craft.description
            Glide.with(itemView.context)
                .load(craft.photoUrl)
                .into(binding.ivImage)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailCraftActivity::class.java)
                intent.putExtra(DetailCraftActivity.DETAIL_CRAFT, craft)
                itemView.context.startActivity(intent)
            }
        }

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchCraftItems>() {
            override fun areItemsTheSame(oldItem: SearchCraftItems, newItem: SearchCraftItems): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SearchCraftItems, newItem: SearchCraftItems): Boolean {
                return oldItem == newItem
            }

        }
    }


}

















