package com.bangkit.rebinmobileapps.view.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bangkit.rebinmobileapps.R
import com.bangkit.rebinmobileapps.databinding.FragmentProfileBinding
import com.bangkit.rebinmobileapps.view.ViewModelFactory
import com.bangkit.rebinmobileapps.view.login.LoginActivity
import com.bangkit.rebinmobileapps.view.main.MainViewModel
import com.bangkit.rebinmobileapps.view.welcome.WelcomeActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())


        // Setup toolbar
        val toolbar: Toolbar = binding.tlbProfile
        toolbar.title = getString(R.string.profile)
        toolbar.inflateMenu(R.menu.menu_profile)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.logout -> {
                    performLogout()
                    Snackbar.make(view, "Logout clicked", Snackbar.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    private fun performLogout() {
        lifecycleScope.launch {
            viewModel.logout()
            val intent = Intent(activity, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            activity?.finish()
        }
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                performLogout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}