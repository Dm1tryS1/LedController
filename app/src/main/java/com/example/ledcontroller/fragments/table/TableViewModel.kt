package com.example.ledcontroller.fragments.table

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ledcontroller.fragments.table.data.Drawing
import kotlinx.coroutines.launch

class TableViewModel(private val dataUseCase: DataUseCase) : ViewModel() {

    fun sendDataForDrawing(data: Drawing){
        viewModelScope.launch {
            dataUseCase.sendDataForDrawing(data)
        }
    }
}