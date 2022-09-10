package com.example.ledcontroller.fragments.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ledcontroller.fragments.table.DataUseCase
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
class HomeViewModel(private val dataUseCase: DataUseCase) : ViewModel() {

    fun sendData(data: String, callback: (flag: Boolean) -> Unit) {
        viewModelScope.launch {
            callback(dataUseCase.testConnection(data))
        }
    }
}