package com.bangkit.rebinmobileapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.data.local.entity.DetectionResultEntity
import com.bangkit.rebinmobileapps.databinding.ItemHistoryBinding
import com.bangkit.rebinmobileapps.util.DateFormat
import com.bangkit.rebinmobileapps.view.detection.DetectionResultViewModel
import com.bumptech.glide.Glide

class HistoryDetectionResultAdapter(
    private val detectionResultViewModel: DetectionResultViewModel
) : RecyclerView.Adapter<HistoryDetectionResultAdapter.HistoryViewHolder>(){

    private val listHistory = ArrayList<DetectionResultEntity>()

    fun setListHistory(listHistory: List<DetectionResultEntity>) {
        val diffCallback = HistoryDiffCallback(this.listHistory, listHistory)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listHistory.clear()
        this.listHistory.addAll(listHistory)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class HistoryViewHolder(
        private val binding: ItemHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(history: DetectionResultEntity) {
            binding.tvLabelHistoryDetection.text = "${history.wasteType}"
            binding.tvAccurayHistoryDetection.text = history.accuracy
            binding.tvCreatedAtHistoryDetection.text = DateFormat.getRelativeTime(history.createdAt)
            Glide.with(binding.root)
                .load(history.imageUrl)
                .placeholder(R.drawable.ic_place_holder)
                .into(binding.ivResulHistoryDetection)
//            binding.btnDelete.setOnClickListener {
//                historyViewModel.delete(history)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(listHistory[position])
    }
}

class HistoryDiffCallback(
    private val oldHistoryList: ArrayList<DetectionResultEntity>,
    private val newHistoryList: List<DetectionResultEntity>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldHistoryList.size

    override fun getNewListSize(): Int = newHistoryList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldHistoryList[oldItemPosition].imageUrl == newHistoryList[newItemPosition].imageUrl
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldHistory = oldHistoryList[oldItemPosition]
        val newHistory = newHistoryList[newItemPosition]
        return oldHistory.imageUrl == newHistory.imageUrl &&
                oldHistory.wasteType == newHistory.wasteType &&
                oldHistory.accuracy == newHistory.accuracy
    }

}












