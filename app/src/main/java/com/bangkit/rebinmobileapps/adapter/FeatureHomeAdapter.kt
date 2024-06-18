package com.bangkit.rebinmobileapps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.data.model.FeatureHome
import com.bangkit.rebinmobileapps.view.main.HomeFragment

class FeatureHomeAdapter(
    private val context: Context,
    private val featureHomeList: List<FeatureHome>,
    private val listener: HomeFragment
): RecyclerView.Adapter<FeatureHomeAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(featureHome: FeatureHome)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_feature_home, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_feature_home)
        val titleTextView: TextView = itemView.findViewById(R.id.tv_feature_home)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val featureHome = featureHomeList[position]
        holder.imageView.setImageResource(featureHome.imageResource)

        // Ganti tanda "-" dengan spasi pada saat menampilkan
        val formattedTitle = featureHome.title.replace("-", " ")
        holder.titleTextView.text = formattedTitle

        holder.itemView.setOnClickListener {
            listener.onItemFeatureClick(featureHome)
        }
    }

    override fun getItemCount(): Int {
        return featureHomeList.size
    }
}