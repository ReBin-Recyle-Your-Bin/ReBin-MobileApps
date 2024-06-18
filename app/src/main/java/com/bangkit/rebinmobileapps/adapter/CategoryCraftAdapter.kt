package com.bangkit.rebinmobileapps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.data.model.CraftCategory
import com.bangkit.rebinmobileapps.view.main.HomeFragment

class CategoryCraftAdapter(
    private val context: Context,
    private val craftList: List<CraftCategory>,
    private val listener: HomeFragment
): RecyclerView.Adapter<CategoryCraftAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(category: CraftCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_craft_category, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_craft_category)
        val titleTextView: TextView = itemView.findViewById(R.id.tv_craft_category)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val craftCategory = craftList[position]
        holder.imageView.setImageResource(craftCategory.imageResource)

        // Ganti tanda "-" dengan spasi pada saat menampilkan
        val formattedTitle = craftCategory.title.replace("-", " ")
        holder.titleTextView.text = formattedTitle

        holder.itemView.setOnClickListener {
            listener.onItemClick(craftCategory)
        }
    }

    override fun getItemCount(): Int {
        return craftList.size
    }
}
