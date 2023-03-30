package com.example.storage

import android.content.Context
import androidx.room.Room
import com.example.storage.database.DeviceInfoDataBase

class DeviceInfoDataBaseProvider(private val context: Context) {
    fun createDataBase() =
        Room.databaseBuilder(context, DeviceInfoDataBase::class.java, "DeviceInfoDataBase").build()
}