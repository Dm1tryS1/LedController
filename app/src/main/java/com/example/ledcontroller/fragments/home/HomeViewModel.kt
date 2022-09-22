package com.example.ledcontroller.fragments.home

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
class HomeViewModel() : ViewModel() {

    fun sendData(data: Int, callback: (flag: Boolean) -> Unit) {
        viewModelScope.launch {

        }
    }
}