package com.example.information_impl.presentation

import com.example.information_impl.presentation.recyclerView.model.InfoViewItem

data class InformationState(val data: List<InfoViewItem.SensorsInfoViewItem>?, val isLoading: Boolean = true)