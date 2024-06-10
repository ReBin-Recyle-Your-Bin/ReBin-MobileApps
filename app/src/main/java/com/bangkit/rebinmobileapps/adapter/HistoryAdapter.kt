package com.bangkit.rebinmobileapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.data.local.entity.History
import com.bangkit.rebinmobileapps.databinding.ItemHistoryBinding
import com.bangkit.rebinmobileapps.util.DateFormat
import com.bangkit.rebinmobileapps.view.history.HistoryViewModel
import com.bumptech.glide.Glide

class HistoryAdapter(
    private val historyViewModel: HistoryViewModel
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>(){

    private val listHistory = ArrayList<History>()

    fun setListHistory(listHistory: List<History>) {
        val diffCallback = HistoryDiffCallback(this.listHistory, listHistory)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listHistory.clear()
        this.listHistory.addAll(listHistory)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class HistoryViewHolder(
        private val binding: ItemHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(history: History) {
            binding.tvPrediction.text = "${history.prediction} ${history.score}"
            binding.tvCreatedAt.text = DateFormat.getRelativeTime(history.createdAt)
            Glide.with(binding.root)
                .load(history.imageUrl)
                .into(binding.ivImage)
            binding.btnDelete.setOnClickListener {
                historyViewModel.delete(history)
            }
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
    private val oldHistoryList: List<History>,
    private val newHistoryList: List<History>
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
                oldHistory.prediction == newHistory.prediction &&
                oldHistory.score == newHistory.score
    }

}












