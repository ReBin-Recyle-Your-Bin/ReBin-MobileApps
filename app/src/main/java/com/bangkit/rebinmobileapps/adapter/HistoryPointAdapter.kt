package com.bangkit.rebinmobileapps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.data.response.PointItem
import com.bangkit.rebinmobileapps.databinding.ItemPointHistoryBinding
import com.bangkit.rebinmobileapps.util.DateFormat

class HistoryPointAdapter: ListAdapter<PointItem, HistoryPointAdapter.MyViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemPointHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val point = getItem(position)
        holder.bind(point)
    }

    inner class MyViewHolder(private val binding : ItemPointHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(point: PointItem) {
            binding.tvPointHistory.text = point.point
            binding.tvDatePointHistory.text = DateFormat.getRelativeTime(point.date)
            binding.tvDescPointHistory.text = point.description
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PointItem>() {
            override fun areItemsTheSame(oldItem: PointItem, newItem: PointItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PointItem, newItem: PointItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}



//class HistoryPointAdapter(private val context: Context, private val listPoint: List<PointHistory>) :
//    RecyclerView.Adapter<HistoryPointAdapter.ViewHolder>() {
//class HistoryDetectionAdapter {
//}
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val binding = ItemPointHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val point = listPoint[position]
//        holder.bind(point)
//    }
//
//    override fun getItemCount(): Int {
//        return listPoint.size
//    }
//
//    inner class ViewHolder(private val binding : ItemPointHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(point: PointHistory) {
//            binding.tvPointHistory.text = point.point.toString()
//            binding.tvDatePointHistory.text = point.date
//            binding.tvDescPointHistory.text = point.description
//        }
//    }
//}