package com.example.smarthome.repository

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import com.example.smarthome.fragments.information.data.Package
import com.example.smarthome.fragments.settings.recyclerView.model.DeviceViewItem
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import kotlin.experimental.or

@SuppressLint("MissingPermission")
class DeviceRepository(applicationContext: Context) {

    private var counter = 0
    private val aPackage =
        Package(null, null, null, null, null, null, null, null)

    private val btAdapter: BluetoothAdapter =
        (applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter

    private var btSocket: BluetoothSocket? = null
    private var outStream: OutputStream? = null

    val isConnected: Boolean
        get() = outStream != null

    private var inStream: InputStream? = null
    private var receiveThread: ReceiveThread? = null

    private var sendData: ((aPackage: Package) -> Unit)? = null

    fun getInfo(callback: (aPackage: Package) -> Unit) {
        sendData = callback
    }

    fun findDevices(): List<DeviceViewItem>? = (btAdapter.bondedDevices)?.map {
        DeviceViewItem(it.name, it.address)
    }


    fun connect(address: String, value: Int): Boolean {
        btSocket?.close()

        try {
            btSocket =
                btAdapter.getRemoteDevice(address)
                    .createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
            Log.d("Сокет", "Создан")

            btAdapter.cancelDiscovery()

            btSocket!!.connect()
            Log.d("Статус", "Соединено")

            outStream = btSocket!!.outputStream
            inStream = btSocket!!.inputStream
            Log.d("Статус", "Поток создан")

            receiveThread = ReceiveThread()
            receiveThread?.start()

            sendTime()
            sendPackage(
                Pair(
                    0x00,
                    ((value * 5).toByte() or 128.toByte()).toInt()
                )
            )
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

    fun disconnect() : Boolean {
        return try {
            btSocket?.close()
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun sendTime(): Boolean {
        val msgBuffer = ByteArray(5)
        val time = Calendar.getInstance(Locale("ru", "RU"))
        msgBuffer[0] = 0.toByte()
        msgBuffer[1] = 0.toByte()
        msgBuffer[2] = time.get(Calendar.HOUR_OF_DAY).toByte()
        msgBuffer[3] = time.get(Calendar.MINUTE).toByte()
        msgBuffer[4] = time.get(Calendar.SECOND).toByte()

        return try {
            outStream!!.write(msgBuffer)
            Log.d(
                "Success",
                "Оправлены: ${msgBuffer[0]},${msgBuffer[1]},${msgBuffer[2]},${msgBuffer[3]},${msgBuffer[4]}"
            )
            true

        } catch (e: Exception) {
            Log.d("Error", "Ошибка отправки")
            false
        }
    }

    fun sendPackage(aPackage: Pair<Int, Int>): Boolean {
        val msgBuffer = ByteArray(2)

        msgBuffer[0] = aPackage.first.toByte()
        msgBuffer[1] = aPackage.second.toByte()

        return try {
            outStream!!.write(msgBuffer)
            Log.d("Success", "Оправлены: $aPackage")
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
                inStream?.read(msgBuffer)
                counter = counter.inc()
                when (counter) {
                    1 -> aPackage.id = msgBuffer[0].toInt()
                    2 -> aPackage.hours = msgBuffer[0].toInt()
                    3 -> aPackage.minutes = msgBuffer[0].toInt()
                    4 -> aPackage.seconds = msgBuffer[0].toInt()
                    5 -> aPackage.info0 = msgBuffer[0]
                    6 -> aPackage.info1 = msgBuffer[0]
                    7 -> aPackage.info2 = msgBuffer[0]
                    8 -> {
                        aPackage.info3 = msgBuffer[0]
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


    inner class ReceiveThread : Thread() {
        override fun run() {
            receiveData()
        }
    }

}