package com.example.smarthome.service.storage.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeviceInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val deviceId: Int,
    val date: String,
    val time: String,
    val value: Int,
)