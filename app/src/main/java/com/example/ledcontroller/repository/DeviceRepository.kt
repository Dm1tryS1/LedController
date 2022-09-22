package com.example.ledcontroller.repository

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.ledcontroller.fragments.information.data.Package
import com.example.ledcontroller.fragments.settings.data.Device
import com.example.ledcontroller.fragments.settings.recyclerView.model.DeviceViewItem
import java.io.InputStream
import java.io.OutputStream
import java.util.*

@SuppressLint("MissingPermission")
class DeviceRepository(applicationContext: Context) {
    private val tag = "BluetoothConnection"

    private var counter = 0
    private val aPackage =
        Package(null, null, null)

    private var btAdapter: BluetoothAdapter? = null
    private var btSocket: BluetoothSocket? = null
    private var outStream: OutputStream? = null
    private var inStream: InputStream? = null
    private var receiveThread: ReceiveThread? = null

    private var sendData: ((aPackage: Package) -> Unit)? = null

    init {
        btAdapter =
            (applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    }

    fun getInfo(callback: (aPackage: Package) -> Unit){
        sendData = callback
    }

    fun findDevices(): List<DeviceViewItem> {
        val btDevices = mutableListOf<DeviceViewItem>()
        (btAdapter?.bondedDevices)?.forEach {
            Log.d(it.name, it.address)
            btDevices.add(DeviceViewItem(it.name, it.address))
        }
        return btDevices
    }

    fun connect(address: String): Boolean {
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
            inStream = btSocket!!.inputStream
            receiveThread = ReceiveThread()
            receiveThread?.start()
            sendData(0)
            Log.d(tag, "Поток создан")
            return true

        } catch (e: Exception) {
            try {
                btSocket!!.close()
                Log.d("Socket", "Закрыт")
            } catch (e: Exception) {
                Log.d("Ошибка", "Нет соединения")
                return false
            }
            Log.d("Ошибка", "Что-то пошло не так")
            return false
        }
    }

    fun sendData(data: Int): Boolean {
        val msgBuffer = ByteArray(1)

        msgBuffer[0] = data.toByte()

        return try {
            outStream!!.write(msgBuffer)
            Log.d("Success", "Оправлены: $data")
            true

        } catch (e: Exception) {
            Log.d("Error", "Ошибка отправки")
            false
        }
    }

    private fun receiveData() {
        val msgBuffer = ByteArray(1)
        while (true) {
            try {
                val size = inStream?.read(msgBuffer)
                counter = counter.inc()
                when (counter) {
                    1 -> aPackage.id = msgBuffer[0].toInt()
                    2 -> aPackage.date = msgBuffer[0].toInt()
                    3 -> {
                        aPackage.info = msgBuffer[0].toInt()
                        counter = 0
                        sendData?.invoke(aPackage)
                    }
                }
                Log.d("Success", "Message: ${msgBuffer[0].toInt()}")
            } catch (i: Exception) {
                break
            }
        }
    }


    inner class ReceiveThread: Thread() {
        override fun run() {
            receiveData()
        }
    }

}