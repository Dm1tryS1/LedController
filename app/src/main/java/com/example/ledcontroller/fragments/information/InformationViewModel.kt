package com.example.ledcontroller.fragments.information

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ledcontroller.R
import com.example.ledcontroller.databinding.ItemInfoBinding
import com.example.ledcontroller.fragments.information.data.Package
import com.example.ledcontroller.fragments.information.recyclerView.model.InfoViewItem

class InformationViewModel(private val getInfoUseCase: GetInfoUseCase) : ViewModel() {

    fun findSensor(): List<InfoViewItem> {
        val data = mutableListOf<InfoViewItem>()
        data.add(InfoViewItem(R.drawable.ic_temperature, 1, "-", "-"))
        data.add(InfoViewItem(R.drawable.ic_conditioner, 2, "-", "-"))
        return data
    }

    fun getInfo(command: Int) {
        getInfoUseCase.getInfo(command)
    }

    fun startObserve(): MutableLiveData<Package> {
        return getInfoUseCase.startObserve()
    }

    fun onMenuClicked(id: Int, view: View) {

    }
}