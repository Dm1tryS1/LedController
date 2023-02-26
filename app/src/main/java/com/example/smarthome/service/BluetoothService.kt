package com.example.smarthome.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.smarthome.common.device.SensorType
import com.example.smarthome.fragments.information.data.Package

import com.example.smarthome.repository.DeviceInfoDataBaseRepository
import com.example.smarthome.repository.DeviceRepository
import com.example.smarthome.service.storage.entity.DeviceInfo
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*


class BluetoothService : Service() {

    private val deviceInfoDataBaseRepository: DeviceInfoDataBaseRepository by inject()

    private var counter = 0
    private val aPackage =
        Package(null, null, null, null, null, null, null, null, null)
    private var receiveThread: ReceiveThread? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        receiveThread = ReceiveThread()
        receiveThread?.start()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun saveInDataBase(aPackage: Package) {
        if (aPackage.type == SensorType.TemperatureSensor.type || aPackage.type == SensorType.HumidifierSensor.type) {
            deviceInfoDataBaseRepository.saveDeviceInfo(
                DeviceInfo(
                    deviceId = aPackage.id!!,
                    time = "${aPackage.hours}:${aPackage.minutes}",
                    value = aPackage.info0!!.toInt(),
                    date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                )
            )
        } else if (aPackage.type == SensorType.PressureSensor.type) {
            deviceInfoDataBaseRepository.saveDeviceInfo(
                DeviceInfo(
                    deviceId = aPackage.id!!,
                    time = "${aPackage.hours}:${aPackage.minutes}",
                    value = ByteBuffer.wrap(
                        byteArrayOf(
                            aPackage.info3!!,
                            aPackage.info2!!,
                            aPackage.info1!!,
                            aPackage.info0!!
                        )
                    ).int,
                    date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
                )
            )
        }
    }

    private fun receiveData() {
        val msgBuffer = ByteArray(1)
        while (true) {
            try {
                DeviceRepository.inStream?.read(msgBuffer)
                counter = counter.inc()
                when (counter) {
                    1 -> aPackage.id = msgBuffer[0].toInt()
                    2 -> aPackage.type = msgBuffer[0].toInt()
                    3 -> aPackage.hours = msgBuffer[0].toInt()
                    4 -> aPackage.minutes = msgBuffer[0].toInt()
                    5 -> aPackage.seconds = msgBuffer[0].toInt()
                    6 -> aPackage.info0 = msgBuffer[0]
                    7 -> aPackage.info1 = msgBuffer[0]
                    8 -> aPackage.info2 = msgBuffer[0]
                    9 -> {
                        aPackage.info3 = msgBuffer[0]
                        counter = 0
                        sendBroadcast(
                            Intent(DeviceRepository.BROADCAST_ACTION).putExtra(
                                DeviceRepository.DEVICE_INFO,
                                Gson().toJson(aPackage)
                            )
                        )
                        saveInDataBase(aPackage)
                    }
                }
                Log.d("12345", "Message: ${msgBuffer[0].toInt()}")
            } catch (i: Exception) {
                break
            }
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    inner class ReceiveThread : Thread() {
        override fun run() {
            receiveData()
        }
    }
}