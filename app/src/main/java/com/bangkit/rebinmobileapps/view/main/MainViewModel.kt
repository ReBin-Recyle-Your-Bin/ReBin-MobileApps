package com.bangkit.rebinmobileapps.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bangkit.rebinmobileapps.data.UserRepository
import com.bangkit.rebinmobileapps.data.model.UserModel

class MainViewModel(private val repository: UserRepository): ViewModel() {

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    suspend fun logout() {
        repository.logout()
    }


}