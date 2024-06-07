package com.bangkit.rebinmobileapps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.data.model.PointHistory
import com.bangkit.rebinmobileapps.databinding.ItemPointHistoryBinding

class HistoryPointAdapter(private val context: Context, private val listPoint: List<PointHistory>) :
    RecyclerView.Adapter<HistoryPointAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPointHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val point = listPoint[position]
        holder.bind(point)
    }

    override fun getItemCount(): Int {
        return listPoint.size
    }

    inner class ViewHolder(private val binding : ItemPointHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(point: PointHistory) {
            binding.tvPointHistory.text = point.point.toString()
            binding.tvDatePointHistory.text = point.date
            binding.tvDescPointHistory.text = point.description
        }
    }
}