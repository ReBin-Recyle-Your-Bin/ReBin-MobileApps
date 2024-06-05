package com.bangkit.rebinmobileapps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.data.model.CraftCategory

class CategoryCraftAdapter(private val context: Context, private val craftList: List<CraftCategory>) :
    RecyclerView.Adapter<CategoryCraftAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_craft_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val craft = craftList[position]
        holder.bind(craft)
    }

    override fun getItemCount(): Int {
        return craftList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.iv_craft_category)
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_craft_category)

        fun bind(craft: CraftCategory) {
            imageView.setImageResource(craft.imageResource)
            titleTextView.text = craft.title
        }
    }
}