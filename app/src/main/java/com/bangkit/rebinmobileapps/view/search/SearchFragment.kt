package com.bangkit.rebinmobileapps.view.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.adapter.SearchCraftAdapter
import com.bangkit.rebinmobileapps.data.ResultState
import com.bangkit.rebinmobileapps.databinding.FragmentSearchBinding
import com.bangkit.rebinmobileapps.view.ViewModelFactory
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchCraftRecyclerView: RecyclerView
    private lateinit var searchCraftAdapter: SearchCraftAdapter

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
                Toast.makeText(context, "Search for $query", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        searchCraftAdapter = SearchCraftAdapter()
        setupRecyclerView()
        setupAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        searchCraftRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = searchCraftAdapter
        }
    }

    private fun setupAction() {
        lifecycleScope.launch {
            viewModel.getCraft().observe(viewLifecycleOwner) { craft ->
                when (craft) {
                    is ResultState.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        val allData = craft.data
                        // log melihat data
                        //Log.d("SearchFragment", "Data: $allData")
                        searchCraftAdapter.submitList(allData)
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
}