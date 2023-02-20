package com.example.smarthome.service.storage

import android.content.Context
import androidx.room.Room
import com.example.smarthome.service.storage.database.DeviceInfoDataBase

class DeviceInfoDataBaseProvider(private val context: Context) {
    fun createDataBase() =
        Room.databaseBuilder(context, DeviceInfoDataBase::class.java, "DeviceInfoDataBase").build()
}