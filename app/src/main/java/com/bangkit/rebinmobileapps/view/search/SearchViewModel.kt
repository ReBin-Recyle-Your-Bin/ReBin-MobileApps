package com.bangkit.rebinmobileapps.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkit.rebinmobileapps.data.UserRepository
import com.bangkit.rebinmobileapps.data.response.CraftPagingItems

class SearchViewModel(private val repository: UserRepository) : ViewModel() {

    val craft: LiveData<PagingData<CraftPagingItems>> =
        repository.getCrafties().cachedIn(viewModelScope)

    fun getCraftState() = repository.getCraft()
}