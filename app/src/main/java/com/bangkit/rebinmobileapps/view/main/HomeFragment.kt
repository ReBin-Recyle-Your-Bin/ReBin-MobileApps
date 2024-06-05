package com.bangkit.rebinmobileapps.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.adapter.CategoryCraftAdapter
import com.bangkit.rebinmobileapps.adapter.StoryInpirationAdapter
import com.bangkit.rebinmobileapps.data.model.CraftCategory
import com.bangkit.rebinmobileapps.data.model.StoryInpiration
import com.bangkit.rebinmobileapps.databinding.FragmentHomeBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    private lateinit var craftRecyclerView: RecyclerView
    private lateinit var storyRecyclerView: RecyclerView
    private lateinit var craftAdapter: CategoryCraftAdapter
    private lateinit var storyInpirationAdapter: StoryInpirationAdapter
    private val craftList = mutableListOf<CraftCategory>() // Isi data sesuai kebutuhan Anda
    private val storyList = mutableListOf<StoryInpiration>() // Isi data sesuai kebutuhan Anda

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        craftRecyclerView = view.findViewById(R.id.rvVariousCrafts)
        storyRecyclerView = view.findViewById(R.id.rvStoryInspiration)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        populateCraftList()
        populateStoryList()
    }

    private fun setupRecyclerView() {
        craftAdapter = CategoryCraftAdapter(requireContext(), craftList)
        craftRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = craftAdapter
        }

        storyInpirationAdapter = StoryInpirationAdapter(requireContext(), storyList)
        storyRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = storyInpirationAdapter
        }
    }

    private fun populateCraftList() {
        craftList.add(CraftCategory(R.drawable.craft_01, "Galon"))
        craftList.add(CraftCategory(R.drawable.craft_02, "Botol Plastik"))
        craftList.add(CraftCategory(R.drawable.craft_03, "Kardus"))
        craftList.add(CraftCategory(R.drawable.craft_04, "Galon"))
        craftList.add(CraftCategory(R.drawable.craft_05, "Botol Plastik"))
        craftList.add(CraftCategory(R.drawable.craft_06, "Kardus"))
        craftList.add(CraftCategory(R.drawable.craft_07, "Galon"))
        craftList.add(CraftCategory(R.drawable.craft_08, "Botol Plastik"))
        craftList.add(CraftCategory(R.drawable.craft_09, "Kardus"))
        craftList.add(CraftCategory(R.drawable.craft_10, "Kardus"))

        craftAdapter.notifyDataSetChanged()
    }

    private fun populateStoryList() {
        // Tambahkan data story ke dalam storyList
        storyList.add(StoryInpiration(R.drawable.craft_01, "Inspirasi 1", "Deskripsi Inspirasi 1"))
        storyList.add(StoryInpiration(R.drawable.craft_02, "Inspirasi 2", "Deskripsi Inspirasi 2"))
        storyList.add(StoryInpiration(R.drawable.craft_03, "Inspirasi 3", "Deskripsi Inspirasi 3"))
        // tambahkan data lainnya
        storyInpirationAdapter.notifyDataSetChanged()
    }


}