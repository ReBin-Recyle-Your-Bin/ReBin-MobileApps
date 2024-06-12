package com.bangkit.rebinmobileapps.view.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.rebinmobileapps.adapter.HistoryDetectionResultAdapter
import com.bangkit.rebinmobileapps.data.local.room.TrashDetectionResultDatabase
import com.bangkit.rebinmobileapps.data.repository.DetectionResultRepository
import com.bangkit.rebinmobileapps.databinding.FragmentHistoryBinding
import com.bangkit.rebinmobileapps.view.detection.DetectionResultViewModel
import com.bangkit.rebinmobileapps.view.detection.DetectionResultViewModelFactory
import com.bangkit.rebinmobileapps.view.factory.HistoryViewModelFactory
import java.util.concurrent.Executors

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HistoryDetectionResultAdapter
    private lateinit var detectionResultViewModel: DetectionResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        setupViewModel()
        setupRecyclerView()
        observeHistory()
        binding.btnClearAll.setOnClickListener {
            detectionResultViewModel.deleteAll()
        }
        return binding.root
    }

    private fun setupViewModel() {
        val application = requireNotNull(this.activity).application
        val dao = TrashDetectionResultDatabase.getDatabase(application).detectionResultDao()
        val repository = DetectionResultRepository.getInstance(dao, Executors.newSingleThreadExecutor())
        val factory = DetectionResultViewModelFactory(repository)
        detectionResultViewModel = ViewModelProvider(this, factory)[DetectionResultViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = HistoryDetectionResultAdapter(detectionResultViewModel)
        binding.recyclerViewHistory.adapter = adapter
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun observeHistory() {
        detectionResultViewModel.getAll().observe(viewLifecycleOwner) { historyList ->
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













