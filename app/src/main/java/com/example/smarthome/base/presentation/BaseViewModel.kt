package com.example.smarthome.base.presentation

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel(), ViewModelInterface {
    override fun onBackPressed(): Boolean = false
}