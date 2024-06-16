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
import com.bangkit.rebinmobileapps.adapter.HistoryDetectionResultAdapter
import com.bangkit.rebinmobileapps.data.ResultState
import com.bangkit.rebinmobileapps.databinding.FragmentHistoryBinding
import com.bangkit.rebinmobileapps.view.ViewModelFactory
import com.bangkit.rebinmobileapps.view.main.MainViewModel
import kotlinx.coroutines.launch
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var historyDetectionResultAdapter: HistoryDetectionResultAdapter
    private lateinit var historyDetectionRecyclerView: RecyclerView

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupAction()
    }

    private fun setupRecyclerView() {
        historyDetectionResultAdapter = HistoryDetectionResultAdapter()
        historyDetectionRecyclerView = binding.rvHistoryDetection
        historyDetectionRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyDetectionResultAdapter
        }
    }

    private fun setupAction() {
        viewModel.getHistoryDetection().observe(viewLifecycleOwner) { historyDetection ->
            when(historyDetection) {
                is ResultState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    historyDetectionResultAdapter.submitList(historyDetection.data)
                    binding.rvHistoryDetection.adapter = historyDetectionResultAdapter

                    historyDetection.data.forEach { Log.d("HistoryFragment", "Data: $it") }
                }
                is ResultState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), historyDetection.error, Toast.LENGTH_SHORT).show()
                }
                is ResultState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

//comment code history fragment baru
//class HistoryFragment : Fragment() {
//
//    private var _binding: FragmentHistoryBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var historyDetectionResultAdapter: HistoryDetectionResultAdapter
//    private lateinit var historyDetectionRecyclerView: RecyclerView
//
//    private val viewModel by viewModels<MainViewModel> {
//        ViewModelFactory.getInstance(requireContext())
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setupRecyclerView()
//        setupAction()
//    }
//
//    private fun setupRecyclerView() {
//        historyDetectionResultAdapter = HistoryDetectionResultAdapter()
//        historyDetectionRecyclerView = binding.rvHistoryDetection
//        historyDetectionRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        historyDetectionRecyclerView.adapter = historyDetectionResultAdapter
//    }
//
//    private fun setupAction() {
//        viewModel.getHistoryDetection().observe(viewLifecycleOwner) { historyDetection ->
//            when(historyDetection) {
//                is ResultState.Success -> {
//                    binding.progressBar.visibility = View.GONE
//                    historyDetectionResultAdapter.submitList(historyDetection.data)
//                    historyDetection.data.forEach { Log.d("HistoryFragment", "Data: $it") }
//                }
//                is ResultState.Error -> {
//                    binding.progressBar.visibility = View.GONE
//                    Toast.makeText(requireContext(), historyDetection.error, Toast.LENGTH_SHORT).show()
//                }
//                is ResultState.Loading -> {
//                    binding.progressBar.visibility = View.VISIBLE
//                }
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}
