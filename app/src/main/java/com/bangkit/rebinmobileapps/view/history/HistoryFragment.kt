package com.bangkit.rebinmobileapps.view.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.rebinmobileapps.adapter.HistoryAdapter
import com.bangkit.rebinmobileapps.databinding.FragmentHistoryBinding
import com.bangkit.rebinmobileapps.view.factory.HistoryViewModelFactory

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HistoryAdapter
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        setupViewModel()
        setupRecyclerView()
        observeHistory()
        binding.btnClearAll.setOnClickListener {
            historyViewModel.deleteAll()
        }
        return binding.root
    }

    private fun setupViewModel() {
        val factory = HistoryViewModelFactory.getInstance(requireActivity().application)
        historyViewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = HistoryAdapter(historyViewModel)
        with(binding.rvHistory) {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    (layoutManager as LinearLayoutManager).orientation
                )
            )
            setHasFixedSize(true)
            adapter = this@HistoryFragment.adapter
        }
    }

    private fun observeHistory() {
        historyViewModel.getAll().observe(viewLifecycleOwner) { historyList ->
            historyList?.let {
                adapter.setListHistory(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}













