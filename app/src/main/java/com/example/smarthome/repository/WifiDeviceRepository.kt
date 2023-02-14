package com.example.smarthome.repository

class WifiDeviceRepository {
    fun checkDeviceConnection(): Boolean {
        return true
    }

    fun connect(ssid: String, password: String): String? {
        return if (true) {
            "192.168.0." + (1..255).random().toString()
        } else {
            null
        }
    }
}