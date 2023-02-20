package com.example.smarthome.service.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeviceInfo(
    @PrimaryKey
    val id: Int,
    val date: String,
    val time: String,
    val value: Int,
)