package com.example.smarthome.fragments.home

import androidx.lifecycle.ViewModel

class HomeViewModel(val homeInteractor: HomeInteractor) : ViewModel() {

    fun checkConnection() : Boolean = homeInteractor.checkConnection()
}