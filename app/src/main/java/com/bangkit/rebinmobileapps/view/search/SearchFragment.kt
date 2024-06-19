package com.bangkit.rebinmobileapps.view.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.adapter.CraftPagingAdapter
import com.bangkit.rebinmobileapps.data.ResultState
import com.bangkit.rebinmobileapps.databinding.FragmentSearchBinding
import com.bangkit.rebinmobileapps.view.ViewModelFactory
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchCraftRecyclerView: RecyclerView
    private lateinit var craftPagingAdapter: CraftPagingAdapter

    private val viewModel by viewModels<SearchViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        searchCraftRecyclerView = binding.rvSearch

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchView =  binding.searchView
        searchView.setIconifiedByDefault(false)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //Toast.makeText(context, "Search for $query", Toast.LENGTH_SHORT).show()
                if (!query.isNullOrEmpty()) {
                    viewModel.searchCraft(query)
                    craftPagingAdapter.refresh()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        craftPagingAdapter = CraftPagingAdapter()
        setupRecyclerView()
        getPagingCraft()
        setupAction()
        setupObservers()
        setupLoadStateListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        searchCraftRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = craftPagingAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter { craftPagingAdapter.retry()}
            )
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.craftCari.observe(viewLifecycleOwner) { pagingData ->
                craftPagingAdapter.submitData(lifecycle, pagingData)
            }
        }
    }

    private fun setupAction() {
        lifecycleScope.launch {
            viewModel.getCraftState().observe(viewLifecycleOwner) { craft ->
                when (craft) {
                    is ResultState.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                    is ResultState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResultState.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), "Error: ${craft.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getPagingCraft() {
        lifecycleScope.launch {
            viewModel.craft.observe(viewLifecycleOwner) { pagingData ->
                craftPagingAdapter.submitData(lifecycle, pagingData)
            }
        }
    }

    private fun setupLoadStateListener() {
        craftPagingAdapter.addLoadStateListener { loadState ->
            // Show loading spinner during initial load or refresh
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            searchCraftRecyclerView.isVisible = loadState.source.refresh !is LoadState.Loading

            // If we have an error state
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.source.refresh as? LoadState.Error

            errorState?.let {
                Toast.makeText(requireContext(), "Error: ${it.error.message}", Toast.LENGTH_SHORT).show()
            }

            // Show empty state if initial load not loading and item count is zero
            if (loadState.source.refresh is LoadState.NotLoading && craftPagingAdapter.itemCount == 0) {
                Toast.makeText(requireContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}