package com.bangkit.rebinmobileapps.view.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.adapter.HistoryDetectionResultAdapter
import com.bangkit.rebinmobileapps.data.ResultState
import com.bangkit.rebinmobileapps.data.local.room.TrashDetectionResultDatabase
import com.bangkit.rebinmobileapps.data.repository.DetectionResultRepository
import com.bangkit.rebinmobileapps.databinding.FragmentHistoryBinding
import com.bangkit.rebinmobileapps.view.ViewModelFactory
import com.bangkit.rebinmobileapps.view.detection.DetectionResultViewModel
import com.bangkit.rebinmobileapps.view.detection.DetectionResultViewModelFactory
import com.bangkit.rebinmobileapps.view.factory.HistoryViewModelFactory
import com.bangkit.rebinmobileapps.view.main.MainViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var historyDetectionRecyclerView: RecyclerView
    private lateinit var historyDetectionResultAdapter: HistoryDetectionResultAdapter
    private lateinit var detectionResultViewModel: DetectionResultViewModel

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        setupRecyclerView()
//        binding.btnClearAll.setOnClickListener {
//            detectionResultViewModel.deleteAll()
//        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupAction()
    }

    private fun setupRecyclerView() {

        historyDetectionRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyDetectionResultAdapter
        }
    }

    private fun setupAction(){
        lifecycleScope.launch {
            viewModel.getHistoryDetection().observe(viewLifecycleOwner) { history_detection ->
                when(history_detection){
                    is ResultState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        historyDetectionResultAdapter.submitList(history_detection.data)
                        binding.rvHistoryDetection.adapter = historyDetectionResultAdapter
                    }
                    is ResultState.Error -> {
                    }
                    is ResultState.Loading -> {
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}













