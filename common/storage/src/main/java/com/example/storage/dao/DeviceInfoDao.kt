package com.example.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.storage.entity.DeviceInfo

@Dao
interface DeviceInfoDao {
    @Query("SELECT * FROM DeviceInfo WHERE deviceId = :id AND date = :date")
    fun getInfoForDeviceByDate(id: Int, date: String): List<DeviceInfo>

    @Insert
    fun insert(deviceInfo: DeviceInfo)
}