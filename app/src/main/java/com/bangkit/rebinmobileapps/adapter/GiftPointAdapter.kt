package com.bangkit.rebinmobileapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.data.response.GiftPointItem
import com.bangkit.rebinmobileapps.databinding.ItemGiftPointBinding
import com.bumptech.glide.Glide

class GiftPointAdapter: ListAdapter<GiftPointItem, GiftPointAdapter.MyViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemGiftPointBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val giftPoint = getItem(position)
        holder.bind(giftPoint)
    }

    inner class MyViewHolder(private val binding: ItemGiftPointBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(giftPoint: GiftPointItem) {
            binding.tvTitleGiftPoint.text = giftPoint.title
            binding.tvPointGift.text = giftPoint.point
            binding.tvDecsGiftPoint.text = giftPoint.description

            Glide.with(itemView.context)
                .load(giftPoint.photoUrl)
                .into(binding.imgGiftPoint)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GiftPointItem>() {
            override fun areItemsTheSame(oldItem: GiftPointItem, newItem: GiftPointItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GiftPointItem, newItem: GiftPointItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}