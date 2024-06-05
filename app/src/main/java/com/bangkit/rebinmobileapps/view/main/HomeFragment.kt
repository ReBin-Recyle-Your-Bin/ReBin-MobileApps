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
import com.bangkit.rebinmobileapps.data.model.CraftCategory
import com.bangkit.rebinmobileapps.databinding.FragmentHomeBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var craftAdapter: CategoryCraftAdapter
    private val craftList = mutableListOf<CraftCategory>() // Isi data sesuai kebutuhan Anda

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = view.findViewById(R.id.rvVariousCrafts)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        populateCraftList() // Isi data craftList sesuai kebutuhan Anda
    }

    private fun setupRecyclerView() {
        craftAdapter = CategoryCraftAdapter(requireContext(), craftList)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = craftAdapter
        }
    }

    private fun populateCraftList() {
        // Tambahkan data craft ke dalam craftList sesuai kebutuhan Anda
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
        // tambahkan data lainnya
        craftAdapter.notifyDataSetChanged()
    }
}