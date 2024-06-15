package com.bangkit.rebinmobileapps.view.search

import androidx.lifecycle.ViewModel
import com.bangkit.rebinmobileapps.data.UserRepository

class SearchViewModel(private val repository: UserRepository) : ViewModel() {

    fun getCraft() = repository.getCraft()
}