package com.bangkit.rebinmobileapps.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.data.response.CraftPagingItems
import com.bangkit.rebinmobileapps.databinding.ItemCraftBinding
import com.bangkit.rebinmobileapps.view.detail.DetailCraftActivity
import com.bumptech.glide.Glide

class CraftPagingAdapter : PagingDataAdapter<CraftPagingItems, CraftPagingAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val craft = getItem(position)
        if (craft != null) {
            holder.bind(craft)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCraftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    inner class MyViewHolder(
        private val binding: ItemCraftBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(craft: CraftPagingItems) {
            binding.tvTitle.text = craft.name
            binding.tvClass.text = craft.className
            binding.tvDescription.text = craft.description
            Glide.with(itemView.context)
                .load(craft.photoUrl)
                .into(binding.ivImage)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailCraftActivity::class.java)
                intent.putExtra(DetailCraftActivity.DETAIL_CRAFT, craft)
                itemView.context.startActivity(
                    intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(itemView.context as Activity)
                        .toBundle())
            }
        }

    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CraftPagingItems>() {
            override fun areItemsTheSame(oldItem: CraftPagingItems, newItem: CraftPagingItems): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CraftPagingItems, newItem: CraftPagingItems): Boolean {
                return oldItem == newItem
            }

        }
    }


}