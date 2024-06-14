package com.bangkit.rebinmobileapps.view.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.adapter.BannerAdapter
import com.bangkit.rebinmobileapps.adapter.CategoryCraftAdapter
import com.bangkit.rebinmobileapps.adapter.StoryInpirationAdapter
import com.bangkit.rebinmobileapps.data.ResultState
import com.bangkit.rebinmobileapps.data.api.ApiService
import com.bangkit.rebinmobileapps.data.model.CraftCategory
import com.bangkit.rebinmobileapps.data.model.StoryInpiration
import com.bangkit.rebinmobileapps.databinding.FragmentHomeBinding
import com.bangkit.rebinmobileapps.view.ViewModelFactory
import com.bangkit.rebinmobileapps.view.history.PointHistoryActivity
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var craftRecyclerView: RecyclerView
    private lateinit var storyRecyclerView: RecyclerView
    private lateinit var craftAdapter: CategoryCraftAdapter
    private lateinit var storyInpirationAdapter: StoryInpirationAdapter
    private val craftList = mutableListOf<CraftCategory>()
    private val storyList = mutableListOf<StoryInpiration>()

    private lateinit var bannerViewPager: ViewPager2
    private lateinit var bannerAdapter: BannerAdapter

    private lateinit var apiService: ApiService

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
        bannerViewPager = binding.vpBanner

        val bannerImages = listOf(
            R.drawable.sample_banner_1,
            R.drawable.sample_banner_2,
            R.drawable.sample_banner_3
        )
        bannerAdapter = BannerAdapter(bannerImages)
        bannerViewPager.adapter = bannerAdapter

        // Auto-scroll functionality
        val handler = Handler()
        val update = Runnable {
            var currentPage = bannerViewPager.currentItem
            currentPage = if (currentPage == bannerImages.size - 1) 0 else currentPage + 1
            bannerViewPager.setCurrentItem(currentPage, true)
        }
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 5000, 5000)

        val btnHistoryPoint = binding.pointHistoryButton

        btnHistoryPoint.setOnClickListener {
            startActivity(Intent(activity, PointHistoryActivity::class.java))
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi adapter sebelum setupRecyclerView dipanggil
        storyInpirationAdapter = StoryInpirationAdapter()
        setupRecyclerView()
        populateCraftList()
        setupAction()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        craftAdapter = CategoryCraftAdapter(requireContext(), craftList)
        craftRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = craftAdapter
        }

        storyRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = storyInpirationAdapter
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
}
