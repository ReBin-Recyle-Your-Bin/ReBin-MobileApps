package com.bangkit.rebinmobileapps.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.R

class BannerAdapter(private val bannerImages: List<Int>) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bannerImageView: ImageView = view.findViewById(R.id.bannerImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.banner_item, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bannerImageView.setImageResource(bannerImages[position])
    }

    override fun getItemCount(): Int {
        return bannerImages.size
    }
}