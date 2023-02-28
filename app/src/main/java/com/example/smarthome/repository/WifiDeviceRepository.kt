package com.example.smarthome.repository

import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log
import com.espressif.iot.esptouch.EsptouchTask
import com.example.smarthome.common.wifi.WifiInfo

class WifiDeviceRepository(private val context: Context) {
    fun getWifiInfo(): WifiInfo? {
        return try {
            val network =
                (context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager).connectionInfo
            WifiInfo(
                ssid = network.ssid.dropLast(1).drop(1),
                bssid = network.bssid,
                ""
            )
        } catch (e: Exception) {
            null
        }
    }

    suspend fun connect(wifiInfo: WifiInfo, callback: (String?) -> Unit) {
        val task = EsptouchTask(wifiInfo.ssid, wifiInfo.bssid, wifiInfo.password, context) //TODO ВЕРНУТь
        task.setPackageBroadcast(false)
        task.setEsptouchListener { response ->
            if (response.isSuc) {
                Log.d("12345", response.inetAddress.toString().drop(1))
                callback(response.inetAddress.toString().drop(1))
                task.interrupt()
            } else {
                callback(null)
                task.interrupt()
            }
        }
        task.executeForResult()

        //callback("192.168.1.42")
    }
}