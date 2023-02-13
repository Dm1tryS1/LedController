package com.example.smarthome.repository

class WifiDeviceRepository {
    fun checkDeviceConnection(): Boolean {
        return true
    }

    fun connect(ssid: String, password: String): List<Byte>? {
        return if (true) {
            listOf(192.toByte(), 168.toByte(), 0.toByte(), 4.toByte())
        } else {
            null
        }
    }
}