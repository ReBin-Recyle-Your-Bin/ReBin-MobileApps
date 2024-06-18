package com.bangkit.rebinmobileapps.view.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.adapter.CategoryCraftAdapter
import com.bangkit.rebinmobileapps.adapter.FeatureHomeAdapter
import com.bangkit.rebinmobileapps.adapter.StoryInpirationAdapter
import com.bangkit.rebinmobileapps.data.ResultState
import com.bangkit.rebinmobileapps.data.model.CraftCategory
import com.bangkit.rebinmobileapps.data.model.FeatureHome
import com.bangkit.rebinmobileapps.databinding.FragmentHomeBinding
import com.bangkit.rebinmobileapps.view.ViewModelFactory
import com.bangkit.rebinmobileapps.view.fitur.challage.ChallangeActivity
import com.bangkit.rebinmobileapps.view.history.PointHistoryActivity
import com.bangkit.rebinmobileapps.view.search.CraftByCategoryActivity
import com.bangkit.rebinmobileapps.view.storyInspiration.StoryInspirationActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var craftRecyclerView: RecyclerView
    private lateinit var storyRecyclerView: RecyclerView
    private lateinit var craftAdapter: CategoryCraftAdapter
    private lateinit var featureHomeAdapter : FeatureHomeAdapter
    private lateinit var storyInpirationAdapter: StoryInpirationAdapter
    private val craftList = mutableListOf<CraftCategory>()
    private val featureHomeList = mutableListOf<FeatureHome>()

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        craftRecyclerView = binding.rvVariousCrafts
        storyRecyclerView = binding.rvStoryInspiration

        val btnHistoryPoint = binding.pointHistoryButton

        btnHistoryPoint.setOnClickListener {
            startActivity(Intent(activity, PointHistoryActivity::class.java))
        }
        binding.tvSeeAllStoryInspiration.setOnClickListener {
            startActivity(Intent(activity, StoryInspirationActivity::class.java))
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storyInpirationAdapter = StoryInpirationAdapter()
        setupRecyclerView()
        populateCraftList()
        featureHomeList()
        setupAction()
        setupPoint()
        setupUserName()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        craftAdapter = CategoryCraftAdapter(requireContext(), craftList, this)
        craftRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = craftAdapter
        }

        storyRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = storyInpirationAdapter
        }

        featureHomeAdapter = FeatureHomeAdapter(requireContext(), featureHomeList, this)
        binding.rvFeatureHome.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = featureHomeAdapter
        }
    }

    fun onItemClick(category: CraftCategory) {
        val intent = Intent(requireContext(), CraftByCategoryActivity::class.java)
        intent.putExtra(CraftByCategoryActivity.CRAFT_CATEGORY, category.title)
        startActivity(intent)
    }

    fun onItemFeatureClick(featureHome: FeatureHome) {
        when (featureHome.title) {
            "Misi" -> {
                startActivity(Intent(requireContext(), ChallangeActivity::class.java))
            }
            "Tukar Point" -> {
                Toast.makeText(requireContext(), "Fitur Tukar Point belum tersedia", Toast.LENGTH_SHORT).show()
            }
            "Jual Karya" -> {
                Toast.makeText(requireContext(), "Fitur Jual Karya belum tersedia", Toast.LENGTH_SHORT).show()
            }
            "Jual Sampah" -> {
                Toast.makeText(requireContext(), "Fitur Jual Sampah belum tersedia", Toast.LENGTH_SHORT).show()
            }
            "Komunitas" -> {
                Toast.makeText(requireContext(), "Fitur Komunitas belum tersedia", Toast.LENGTH_SHORT).show()
            }
            "Event" -> {
                Toast.makeText(requireContext(), "Fitur Event belum tersedia", Toast.LENGTH_SHORT).show()
            }
            "Pelatihan" -> {
                Toast.makeText(requireContext(), "Fitur Pelatihan belum tersedia", Toast.LENGTH_SHORT).show()
            }
            "Kerjasama" -> {
                Toast.makeText(requireContext(), "Fitur Kerjasama belum tersedia", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupAction() {
        lifecycleScope.launch {
            viewModel.getStoryInspiration().observe(viewLifecycleOwner) { story ->
                when (story) {
                    is ResultState.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        val limitedData = story.data.take(3)
                        storyInpirationAdapter.submitList(limitedData)
                    }
                    is ResultState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResultState.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), "Error: ${story.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupPoint() {
        lifecycleScope.launch {
            viewModel.getPointHistory().observe(viewLifecycleOwner) { pointHistory ->
                when (pointHistory) {
                    is ResultState.Success -> {
                        val totalPoints = pointHistory.data
                            .filter { it.status == "entry" }
                            .sumBy { it.point.toIntOrNull() ?: 0 }
                        binding.tvTotalPoint.text = totalPoints.toString()
                    }
                    is ResultState.Loading -> {
                    }
                    is ResultState.Error -> {
                        Toast.makeText(requireContext(), "Error: ${pointHistory.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupUserName() {

        lifecycleScope.launch {
            viewModel.getProfile().observe(viewLifecycleOwner) { user ->
                when (user) {
                    is ResultState.Success -> {
                        val username = user.data.name
                        binding.welcomeTextView.text = "Selamat Datang,\n$username"
                    }
                    is ResultState.Loading -> {
                    }
                    is ResultState.Error -> {
                    }
                }
            }
        }
    }

    private fun populateCraftList() {
        craftList.add(CraftCategory(R.drawable.craft_01, "Galon"))
        craftList.add(CraftCategory(R.drawable.craft_02, "Cup-Gelas"))
        craftList.add(CraftCategory(R.drawable.craft_03, "Botol-Plastik"))
        craftList.add(CraftCategory(R.drawable.craft_04, "Bungkus-Plastik"))
        craftList.add(CraftCategory(R.drawable.craft_05, "Ban"))
        craftList.add(CraftCategory(R.drawable.craft_06, "Kaleng"))
        craftList.add(CraftCategory(R.drawable.craft_07, "Kaca"))
        craftList.add(CraftCategory(R.drawable.craft_08, "Kardus"))
        craftList.add(CraftCategory(R.drawable.craft_09, "Kertas"))
        craftList.add(CraftCategory(R.drawable.craft_10, "Sampah-Organik"))

        craftAdapter.notifyDataSetChanged()
    }

    private fun featureHomeList(){
        featureHomeList.add(FeatureHome(R.drawable.ftr_challange, "Misi"))
        featureHomeList.add(FeatureHome(R.drawable.ftr_tukar_point, "Tukar Point"))
        featureHomeList.add(FeatureHome(R.drawable.ftr_jual_craft, "Jual Karya"))
        featureHomeList.add(FeatureHome(R.drawable.ftr_jual_sampah, "Jual Sampah"))
        featureHomeList.add(FeatureHome(R.drawable.ftr_komunitas, "Komunitas"))
        featureHomeList.add(FeatureHome(R.drawable.ftr_event, "Event"))
        featureHomeList.add(FeatureHome(R.drawable.ftr_course, "Pelatihan"))
        featureHomeList.add(FeatureHome(R.drawable.ftr_kerjasama, "Kerjasama"))

        featureHomeAdapter.notifyDataSetChanged()
    }
}
