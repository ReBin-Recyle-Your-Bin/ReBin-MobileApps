package com.bangkit.rebinmobileapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.data.local.entity.DetectionResultEntity
import com.bangkit.rebinmobileapps.data.response.HistoryDetectionItem
import com.bangkit.rebinmobileapps.databinding.ItemHistoryBinding
import com.bangkit.rebinmobileapps.util.DateFormat
import com.bangkit.rebinmobileapps.view.detection.DetectionResultViewModel
import com.bumptech.glide.Glide

class HistoryDetectionResultAdapter: ListAdapter<HistoryDetectionItem, HistoryDetectionResultAdapter.HistoryViewHolder>(DIFF_CALLBACK) {

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
            binding.tvAccurayHistoryDetection.text = history.accuracy
            binding.tvCreatedAtHistoryDetection.text = DateFormat.getRelativeTime(history.date)
//            binding.btnDelete.setOnClickListener {
//                historyViewModel.delete(history)
//            }
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryDetectionItem>() {
            override fun areItemsTheSame(oldItem: HistoryDetectionItem, newItem: HistoryDetectionItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: HistoryDetectionItem, newItem: HistoryDetectionItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

//class HistoryDiffCallback(
//    private val oldHistoryList: ArrayList<DetectionResultEntity>,
//    private val newHistoryList: List<DetectionResultEntity>
//): DiffUtil.Callback() {
//    override fun getOldListSize(): Int = oldHistoryList.size
//
//    override fun getNewListSize(): Int = newHistoryList.size
//
//    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return oldHistoryList[oldItemPosition].imageUrl == newHistoryList[newItemPosition].imageUrl
//    }
//
//    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        val oldHistory = oldHistoryList[oldItemPosition]
//        val newHistory = newHistoryList[newItemPosition]
//        return oldHistory.imageUrl == newHistory.imageUrl &&
//                oldHistory.wasteType == newHistory.wasteType &&
//                oldHistory.accuracy == newHistory.accuracy
//    }
//
//}












