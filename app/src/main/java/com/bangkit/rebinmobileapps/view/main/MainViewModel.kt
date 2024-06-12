package com.bangkit.rebinmobileapps.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.bangkit.rebinmobileapps.data.UserRepository
import com.bangkit.rebinmobileapps.data.model.UserModel
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository): ViewModel() {
    
    val userSession: LiveData<UserModel> = repository.getSession().asLiveData()

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }


}