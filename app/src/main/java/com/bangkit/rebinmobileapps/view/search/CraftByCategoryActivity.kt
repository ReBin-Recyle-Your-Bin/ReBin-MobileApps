package com.bangkit.rebinmobileapps.view.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.adapter.SearchCraftAdapter
import com.bangkit.rebinmobileapps.data.ResultState
import com.bangkit.rebinmobileapps.databinding.ActivityCraftByCategoryBinding
import com.bangkit.rebinmobileapps.view.ViewModelFactory
import com.bangkit.rebinmobileapps.view.main.MainViewModel
import kotlinx.coroutines.launch

class CraftByCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCraftByCategoryBinding
    private lateinit var craftCategoryRecyclerView: RecyclerView

    private val craftByCategoryAdapter = SearchCraftAdapter()

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCraftByCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val craftCategory = intent.getStringExtra(CRAFT_CATEGORY)

        setupRecyclerView()
        setupAction("$craftCategory")

        val toolbar = binding.tblCraftByCategory
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Category $craftCategory"

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupRecyclerView(){
        craftCategoryRecyclerView = binding.rvCraftByCategory
        craftCategoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CraftByCategoryActivity)
            adapter = craftByCategoryAdapter
        }
    }

    private fun setupAction(craftCategory: String){
        lifecycleScope.launch {
            viewModel.getCraftByCategory(craftCategory).observe(this@CraftByCategoryActivity) { craft ->
                when (craft) {
                    is ResultState.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        craftByCategoryAdapter.submitList(craft.data)
                        binding.rvCraftByCategory.adapter = craftByCategoryAdapter
                    }
                    is ResultState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResultState.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(this@CraftByCategoryActivity, craft.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    companion object{
        const val CRAFT_CATEGORY = "craft_category"
    }
}