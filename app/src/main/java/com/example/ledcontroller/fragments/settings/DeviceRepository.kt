package com.example.ledcontroller.fragments.settings

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import java.io.IOException
import java.io.OutputStream
import java.util.*

@SuppressLint("MissingPermission")
class DeviceRepository(applicationContext: Context) {
    private val tag = "BluetoothConnection"

    private var btAdapter: BluetoothAdapter? = null
    private var btSocket: BluetoothSocket? = null
    private var outStream: OutputStream? = null

    init {
        btAdapter =
            (applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }

    fun findDevices(): MutableMap<String, String> {
        val btDevices = mutableMapOf<String, String>()
        (btAdapter?.bondedDevices)?.forEach {
            Log.d(it.name, it.address)
            btDevices[it.name] = it.address
        }
        return btDevices
    }

    fun connect(address: String) : Boolean {
        Log.d(tag, "Соединение ")

        try {
            val device = btAdapter?.getRemoteDevice(address)
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

            btSocket = device!!.createRfcommSocketToServiceRecord(uuid)
            Log.d(tag, "Создан")

            btAdapter!!.cancelDiscovery()

            btSocket!!.connect()
            Log.d(tag, "Соединено")

            outStream = btSocket!!.outputStream
            Log.d(tag, "Поток создан")
            return true

        } catch (e: Exception){
            try {
                btSocket!!.close()
                Log.d("Socket", "Закрыт")
            } catch (e: Exception){
                Log.d("Ошибка", "Нет соединения")
                return false
            }
            Log.d("Ошибка", "Что-то пошло не так")
            return false
        }
    }

    fun sendData(data: String) {
        val msgBuffer = data.toByteArray()
        try {
            Log.d("Success", "Данные $data")
            outStream?.write(msgBuffer)
            Log.d("Success", "Оправлены")
        } catch (e: IOException) {
            Log.d("Error", "Ошибка отправки")
        }
    }

}