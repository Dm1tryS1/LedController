package com.example.smarthome.repository

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.example.smarthome.common.device.Command
import com.example.smarthome.common.device.CommandsForMaster
import com.example.smarthome.fragments.information.data.Package
import com.example.smarthome.fragments.settings.recyclerView.model.DeviceViewItem
import com.example.smarthome.service.BluetoothService
import com.google.gson.Gson
import kotlinx.coroutines.delay
import java.io.InputStream
import java.io.OutputStream
import java.util.*


@SuppressLint("MissingPermission")
class DeviceRepository(private val applicationContext: Context) {

    private val btAdapter: BluetoothAdapter =
        (applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter

    private var btSocket: BluetoothSocket? = null
    private var outStream: OutputStream? = null

    val isConnected: Boolean
        get() = btSocket?.isConnected == true


    fun getInfo(callback: (aPackage: Package) -> Unit) {
        sendData = callback
    }

    fun findDevices(): List<DeviceViewItem>? = (btAdapter.bondedDevices)?.map {
        DeviceViewItem(it.name, it.address)
    }


    suspend fun connect(
        address: String,
        timer: Int?,
        maxTemp: Int?,
        minTemp: Int?,
        maxHum: Int?,
        minHum: Int?,
        displayedValue: Int?
    ): Boolean {
        btSocket?.close()

        try {
            btSocket =
                btAdapter.getRemoteDevice(address)
                    .createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
            Log.d("12345", "Создан")

            btAdapter.cancelDiscovery()

            btSocket!!.connect()
            Log.d("12345", "Соединено")

            outStream = btSocket!!.outputStream
            inStream = btSocket!!.inputStream
            Log.d("12345", "Поток создан")


            applicationContext.startService(
                Intent(
                    applicationContext,
                    BluetoothService::class.java
                )
            )

            val filter = IntentFilter(BROADCAST_ACTION)
            applicationContext.registerReceiver(DeviceReceiver(), filter)

            sendPackage(Command.MasterSendDate())
            timer?.let { sendPackage(Command.MasterCommand(CommandsForMaster.SetTimer, it * 5)) }
            delay(100)
            maxTemp?.let {
                sendPackage(
                    Command.MasterCommand(
                        CommandsForMaster.SetMaxTemperature,
                        it
                    )
                )
            }
            delay(100)
            minTemp?.let {
                sendPackage(
                    Command.MasterCommand(
                        CommandsForMaster.SetMinTemperature,
                        it
                    )
                )
            }
            delay(100)
            maxHum?.let { sendPackage(Command.MasterCommand(CommandsForMaster.SetMaxHumidity, it)) }
            delay(100)
            minHum?.let { sendPackage(Command.MasterCommand(CommandsForMaster.SetMinHumidity, it)) }
            delay(100)
            displayedValue?.let {
                sendPackage(
                    Command.MasterCommand(
                        CommandsForMaster.SetDisplayedValue,
                        it
                    )
                )
            }

            return true

        } catch (e: Exception) {
            try {
                btSocket!!.close()
                Log.d("12345", "Закрыт")
            } catch (e: Exception) {
                Log.d("12345", "Нет соединения")
                return false
            }
            Log.d("12345", "Что-то пошло не так")
            return false
        }
    }

    fun disconnect(): Boolean {
        return try {
            btSocket?.close()
            true
        } catch (e: Exception) {
            false
        }
    }

    fun sendPackage(aPackage: Command): Boolean {
        if (aPackage is Command.MasterSendDate) {
            val time = Calendar.getInstance(Locale("ru", "RU"))
            aPackage.msgBuffer.apply {
                add(time.get(Calendar.HOUR_OF_DAY))
                add(time.get(Calendar.MINUTE))
                add(time.get(Calendar.SECOND))
            }
        }

        return try {
            outStream!!.write(aPackage.msgBuffer.map { it.toByte() }.toByteArray())
            Log.d("12345", "Оправлены")
            true
        } catch (e: Exception) {
            Log.d("12345", "Ошибка отправки")
            false
        }
    }

    companion object {
        const val EndOfTransmission = 255.toByte()
        const val More = 254.toByte()
        const val Less = 255.toByte()
        var inStream: InputStream? = null
        var sendData: ((aPackage: Package) -> Unit)? = null
        const val DEVICE_INFO = "DEVICE_INFO"
        const val BROADCAST_ACTION = "BROADCAST_ACTION"
    }

    inner class DeviceReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            try {
                val aPackage =
                    Gson().fromJson(intent?.getStringExtra(DEVICE_INFO), Package::class.java)
                sendData?.invoke(aPackage)
            } catch (_: Exception) { }
        }
    }

}