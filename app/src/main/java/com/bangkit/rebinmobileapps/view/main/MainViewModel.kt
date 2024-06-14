package com.bangkit.rebinmobileapps.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkit.rebinmobileapps.data.UserRepository
import com.bangkit.rebinmobileapps.data.model.UserModel
import com.bangkit.rebinmobileapps.data.response.SearchCraftItems
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository): ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getStoryInspiration() = repository.getStoryInspiration()

    fun getHistoryDetection() = repository.getHistoryDetection()

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }


}