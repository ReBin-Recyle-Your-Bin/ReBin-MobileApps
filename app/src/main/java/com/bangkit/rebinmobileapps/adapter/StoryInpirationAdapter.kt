package com.bangkit.rebinmobileapps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.data.model.StoryInpiration

class StoryInpirationAdapter(private val context: Context, private val storyList: List<StoryInpiration>) :
    RecyclerView.Adapter<StoryInpirationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_story, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = storyList[position]
        holder.bind(story)
    }

    override fun getItemCount(): Int {
        return storyList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.iv_story_inpiration)
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_story_inpiration_title)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.tv_story_inpiration_description)

        fun bind(story: StoryInpiration) {
            imageView.setImageResource(story.imageResource)
            titleTextView.text = story.title
            descriptionTextView.text = story.description
        }
    }
}