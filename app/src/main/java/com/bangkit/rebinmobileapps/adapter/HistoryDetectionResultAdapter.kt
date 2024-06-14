package com.bangkit.rebinmobileapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.data.response.HistoryDetectionItem
import com.bangkit.rebinmobileapps.databinding.ItemHistoryBinding
import com.bangkit.rebinmobileapps.util.DateFormat
import android.util.Log

class HistoryDetectionResultAdapter : ListAdapter<HistoryDetectionItem, HistoryDetectionResultAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = getItem(position)
        holder.bind(history)
    }

    inner class HistoryViewHolder(
        private val binding: ItemHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(history: HistoryDetectionItem) {
            binding.tvLabelHistoryDetection.text = history.label
            binding.tvAccurayHistoryDetection.text = history.accuracy.toString()
            binding.tvCreatedAtHistoryDetection.text = DateFormat.getRelativeTime(history.date)

            Log.d("HistoryViewHolder", "Bind data: $history")
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryDetectionItem>() {
            override fun areItemsTheSame(oldItem: HistoryDetectionItem, newItem: HistoryDetectionItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: HistoryDetectionItem, newItem: HistoryDetectionItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
