package com.example.ledcontroller.fragments.home

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
class HomeViewModel(private val dataUseCase: DataUseCase) : ViewModel() {

    fun sendData(data: String, callback: (flag: Boolean) -> Unit) {
        viewModelScope.launch {
            callback(dataUseCase.sendData(data))
        }
    }
}