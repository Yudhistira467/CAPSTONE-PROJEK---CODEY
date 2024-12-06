package com.example.codey.ui.materi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codey.data.ApiConfig
import kotlinx.coroutines.launch

class MateriViewModel : ViewModel() {
    private val _materiList = MutableLiveData<List<DataMateri>>()
    val materiList: LiveData<List<DataMateri>> get() = _materiList

    init {
        fetchMateri()
    }

    private fun fetchMateri() {
        viewModelScope.launch {
            try {
                val materiList = ApiConfig.getMateriApiService().getMateri()
                _materiList.postValue(materiList)
            } catch (e: Exception) {
                Log.e("MateriViewModel", "Error fetching materi: ${e.message}")
            }
        }
    }
}