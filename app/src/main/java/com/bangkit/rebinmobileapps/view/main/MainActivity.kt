package com.bangkit.rebinmobileapps.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.databinding.ActivityMainBinding
import com.bangkit.rebinmobileapps.view.detection.DetectionActivity
import com.bangkit.rebinmobileapps.view.history.HistoryFragment
import com.bangkit.rebinmobileapps.view.profile.ProfileFragment
import com.bangkit.rebinmobileapps.view.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView: BottomNavigationView = binding.bottomNavigationView

        replace(HomeFragment())

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> replace(HomeFragment())
                R.id.nav_search -> replace(SearchFragment())
                R.id.nav_history -> replace(HistoryFragment())
                R.id.nav_profile -> replace(ProfileFragment())
            }
            true
        }

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        binding.fabBtnDetection.setOnClickListener {
            intent = Intent(this, DetectionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun replace(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.navHost, fragment)
        fragmentTransaction.commit()
    }


}