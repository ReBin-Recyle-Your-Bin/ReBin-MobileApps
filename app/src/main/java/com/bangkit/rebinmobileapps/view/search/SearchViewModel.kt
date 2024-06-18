package com.bangkit.rebinmobileapps.view.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bangkit.rebinmobileapps.data.UserRepository
import com.bangkit.rebinmobileapps.data.response.SearchCraftItems
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: UserRepository) : ViewModel() {

    val craft: LiveData<PagingData<SearchCraftItems>> =
        repository.getCrafties().cachedIn(viewModelScope)

    fun getCraftState() = repository.getCraft()

    //coba-coba search
    private val queryLiveData = MutableLiveData<String>()
    private val allCraftsLiveData = MutableLiveData<List<SearchCraftItems>>()

    init {
        fetchAllCrafts()
    }

    val craftCari: LiveData<PagingData<SearchCraftItems>> = queryLiveData.switchMap { query ->
        val filteredCrafts = if (query.isNullOrEmpty()) {
            allCraftsLiveData.value ?: emptyList()
        } else {
            allCraftsLiveData.value?.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            } ?: emptyList()
        }
        MutableLiveData(PagingData.from(filteredCrafts))
    }

    private fun fetchAllCrafts() {
        viewModelScope.launch {
            val allCrafts = mutableListOf<SearchCraftItems>()
            var page = 1
            while (true) {
                val response = repository.getCraftPage(page)
                if (response.isEmpty()) break
                allCrafts.addAll(response)
                page++
            }
            allCraftsLiveData.value = allCrafts
        }
    }

    fun searchCraft(query: String) {
        queryLiveData.value = query
    }
}















