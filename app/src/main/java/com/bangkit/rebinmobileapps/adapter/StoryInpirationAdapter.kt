package com.bangkit.rebinmobileapps.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.data.response.StoryItem
import com.bangkit.rebinmobileapps.databinding.ItemStoryBinding
import com.bangkit.rebinmobileapps.view.detail.DetailStoryActivity
import com.bumptech.glide.Glide

class StoryInpirationAdapter : ListAdapter<StoryItem, StoryInpirationAdapter.MyViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)
    }

    inner class MyViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(story: StoryItem) {
            binding.tvStoryInpirationTitle.text = story.title
            binding.tvStoryInpirationDescription.text = story.description

            Glide.with(itemView.context)
                .load(story.photoUrl)
                .into(binding.ivStoryInpiration)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                intent.putExtra(DetailStoryActivity.DETAIL_STORY_INSPIRATION, story)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryItem>() {
            override fun areItemsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}