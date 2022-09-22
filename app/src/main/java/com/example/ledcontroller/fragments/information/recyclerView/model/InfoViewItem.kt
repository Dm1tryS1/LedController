package com.example.ledcontroller.fragments.information.recyclerView.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InfoViewItem(val iconId: Int, val id: Int, val info: String, val date: String) : Parcelable

