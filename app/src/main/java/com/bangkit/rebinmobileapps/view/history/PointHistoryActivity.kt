package com.bangkit.rebinmobileapps.view.history

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.adapter.HistoryPointAdapter
import com.bangkit.rebinmobileapps.data.ResultState
import com.bangkit.rebinmobileapps.data.model.PointHistory
import com.bangkit.rebinmobileapps.databinding.ActivityPointHistoryBinding
import com.bangkit.rebinmobileapps.view.ViewModelFactory
import com.bangkit.rebinmobileapps.view.main.MainViewModel

class PointHistoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPointHistoryBinding
    private val historyPointAdapter = HistoryPointAdapter()

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPointHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupAction()

        binding.tlbHistoryPoint.setNavigationOnClickListener {
            onBackPressed()
        }

//        val historyPointRV : RecyclerView = binding.rvHistoryPoint
//        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        historyPointRV.layoutManager = layoutManager
//
//        val listPoint = ArrayList<PointHistory>(
//            listOf(
//                PointHistory(10, "10/10/2021", "Poin didapat dari membagikan cerita"),
//                PointHistory(20, "11/10/2021", "Poin didapat dari membagikan cerita"),
//                PointHistory(30, "12/10/2021", "Poin didapat dari membagikan cerita"),
//                PointHistory(40, "13/10/2021", "Poin didapat dari membagikan cerita"),
//                PointHistory(50, "14/10/2021", "Poin didapat dari membagikan cerita"),
//                PointHistory(60, "15/10/2021", "Poin didapat dari membagikan cerita"),
//                PointHistory(70, "16/10/2021", "Poin didapat dari membagikan cerita"),
//                PointHistory(80, "17/10/2021", "Poin didapat dari membagikan cerita"),
//                PointHistory(90, "18/10/2021", "Poin didapat dari membagikan cerita"),
//                PointHistory(100, "19/10/2021", "Poin didapat dari membagikan cerita"),
//            )
//        )
//        val adapter = HistoryPointAdapter(this, listPoint)
//        historyPointRV.adapter = adapter
    }

    private fun setupRecyclerView() {
        val historyPointRV : RecyclerView = binding.rvHistoryPoint
        historyPointRV.apply {
            layoutManager = LinearLayoutManager(this@PointHistoryActivity)
            adapter = historyPointAdapter
        }
    }

    private fun setupAction() {
        viewModel.getPointHistory().observe(this@PointHistoryActivity) { pointHistory ->
            when (pointHistory) {
                is ResultState.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    historyPointAdapter.submitList(pointHistory.data)
                    binding.rvHistoryPoint.adapter = historyPointAdapter
                }
                is ResultState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ResultState.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this, "Error: ${pointHistory.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        const val DETAIL_POINT_HISTORY = "detail_point_history"
    }
}