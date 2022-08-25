package com.example.ledcontroller

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ledcontroller.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.OutputStream
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "BluetoothConnection"

    private var btAdapter: BluetoothAdapter? = null
    private var btSocket: BluetoothSocket? = null
    private var outStream: OutputStream? = null
    private var btDevices = mutableListOf<BluetoothDevice>()

    private fun init() {
        val btManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = btManager.adapter
        (btAdapter?.bondedDevices)?.forEach {
            Log.d(it.name, it.address)
            btDevices.add(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        btAdapter = (getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter

        binding.btnOn.setOnClickListener {
            sendData("1")
        }
        binding.btnOff.setOnClickListener {
            sendData("0")
        }

        init()
        connect()
    }

    private fun connect() {
        Log.d(TAG, "Соединение ")
        GlobalScope.launch {
            val device = btAdapter?.getRemoteDevice(btDevices[0]!!.address) // ВСТАВИТЬ СВОЙ MAC
            val uuid = UUID.fromString(device!!.uuids[5]!!.toString()) // ВСТАВИТЬ ОДИН ИЗ UUID

            device!!.uuids.forEach {
                Log.d("uuid", it.toString())
            }

            try {
                btSocket = device!!.createRfcommSocketToServiceRecord(uuid)
                Log.d(TAG, "Создан")
            } catch (e: IOException) {
                Log.d("Ошибка", "Не создан сокет: " + e.message + ".")
            }

            btAdapter!!.cancelDiscovery()

            try {
                btSocket!!.connect()
                Log.d(TAG, "Соединено")
            } catch (e: IOException) {
                try {
                    btSocket!!.close()
                    Log.d("Socket", "Закрыт")
                } catch (e: Exception) {
                    Log.d("Ошибка", "Нет соединения")
                }
            }

            try {
                outStream = btSocket!!.outputStream
                Log.d(TAG, "Поток создан")
            } catch (e: IOException) {
                Log.d("Error","Поток не создан")
            }
        }
    }

    private fun sendData(message: String) {
        val msgBuffer = message.toByteArray()
        try {
            Log.d("Success", "Данные $message")
            outStream?.write(msgBuffer)
            Log.d("Success", "Оправлены")
        } catch (e: IOException) {
            Log.d("Error", "Ошибка отправки")
        }
    }
}