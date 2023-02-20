package com.example.smarthome.service.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.smarthome.service.storage.dao.DeviceInfoDao
import com.example.smarthome.service.storage.entity.DeviceInfo

@Database(entities = [DeviceInfo::class], version = 1)
abstract class DeviceInfoDataBase : RoomDatabase() {
    abstract fun deviceInfoDao(): DeviceInfoDao
}