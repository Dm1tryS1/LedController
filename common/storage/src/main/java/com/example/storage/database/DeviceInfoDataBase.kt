package com.example.storage.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.storage.dao.DeviceInfoDao
import com.example.storage.entity.DeviceInfo

@Database(entities = [DeviceInfo::class], version = 1)
abstract class DeviceInfoDataBase : RoomDatabase() {
    abstract fun deviceInfoDao(): DeviceInfoDao
}