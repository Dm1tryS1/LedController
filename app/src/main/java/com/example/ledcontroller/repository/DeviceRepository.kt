package com.example.ledcontroller.repository

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import com.example.ledcontroller.fragments.information.data.Package
import com.example.ledcontroller.fragments.settings.recyclerView.model.DeviceViewItem
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import kotlin.experimental.or

@SuppressLint("MissingPermission")
class DeviceRepository(applicationContext: Context) {
    private val tag = "BluetoothConnection"

    private var counter = 0
    private val aPackage =
        Package(null, null, null, null, null)

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

    fun getInfo(callback: (aPackage: Package) -> Unit) {
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

    fun connect(address: String, value: Int): Boolean {
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
            sendTime()
            sendPackage(
                Pair(
                    0x00,
                    ((value * 5).toByte() or 128.toByte()).toInt()
                )
            )
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

    private fun sendTime(): Boolean {
        val msgBuffer = ByteArray(5)
        val time = Calendar.getInstance()
        msgBuffer[0] = 0.toByte()
        msgBuffer[1] = 0.toByte()
        msgBuffer[2] = time.get(Calendar.HOUR).toByte()
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
                    5 -> {
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


    inner class ReceiveThread : Thread() {
        override fun run() {
            receiveData()
        }
    }

}