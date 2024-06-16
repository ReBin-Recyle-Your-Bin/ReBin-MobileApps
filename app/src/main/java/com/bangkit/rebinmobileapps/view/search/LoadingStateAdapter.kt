package com.bangkit.rebinmobileapps.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.databinding.ItemLoadingBinding

class LoadingStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadingStateAdapter.LoadingStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingStateViewHolder(binding, retry)
    }
    class LoadingStateViewHolder(
        private val binding: ItemLoadingBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadstate: LoadState) {
            if (loadstate is LoadState.Error) {
                binding.errorMsg.text = loadstate.error.localizedMessage
            }

            binding.progressBar.isVisible = loadstate is LoadState.Loading
            binding.retryButton.isVisible = loadstate is LoadState.Error
            binding.errorMsg.isVisible = loadstate is LoadState.Error
        }
    }
}