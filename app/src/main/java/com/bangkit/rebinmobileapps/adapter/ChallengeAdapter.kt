package com.bangkit.rebinmobileapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.data.response.ChallengeItem
import com.bangkit.rebinmobileapps.databinding.ItemChallengeBinding
import com.bangkit.rebinmobileapps.util.DateFormat
import com.bumptech.glide.Glide

class ChallengeAdapter : ListAdapter<ChallengeItem, ChallengeAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemChallengeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val challenge = getItem(position)
        holder.bind(challenge)
    }

    inner class MyViewHolder(private val binding: ItemChallengeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(challenge: ChallengeItem) {
            binding.tvDescPointHistory.text = challenge.title
            binding.tvPointChallage.text = challenge.point
            binding.tvDateChallenge.text = DateFormat.getRelativeTime(challenge.expired)

            Glide.with(itemView.context)
                .load(challenge.photoUrl)
                .into(binding.ivChallenge)
        }
    }
    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ChallengeItem>() {
            override fun areItemsTheSame(oldItem: ChallengeItem, newItem: ChallengeItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ChallengeItem, newItem: ChallengeItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}