package com.example.smarthome.repository

import android.content.Context
import android.net.wifi.WifiManager
import com.espressif.iot.esptouch2.provision.*
import com.example.smarthome.repository.model.WifiInfo
import com.example.smarthome.utils.toBytes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.nio.charset.StandardCharsets


class WifiDeviceRepository(private val context: Context) {

    private val provisioner = EspProvisioner(context)
    fun getWifiInfo(): WifiInfo {
        val network =
            (context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager).connectionInfo
        return WifiInfo(ssid = network.ssid.dropLast(1).drop(1), bssid = network.bssid, "")
    }

    suspend fun connect(wifiInfo: WifiInfo, callback: (String?) -> Unit) {
        try {
            val syncListener = object : EspSyncListener {
                override fun onStart() {}
                override fun onStop() {}
                override fun onError(e: Exception) {
                    provisioner.stopSync()
                }
            }
            provisioner.startSync(syncListener)
            delay(5000)
            provisioner.stopSync()

            val request = EspProvisioningRequest.Builder(context)
                .setSSID(wifiInfo.ssid.toByteArray())
                .setBSSID(wifiInfo.bssid.toBytes(":"))
                .setPassword(wifiInfo.password.toByteArray())
                .build()

            val listener = object : EspProvisioningListener {
                override fun onStart() {}
                override fun onResponse(result: EspProvisioningResult) {
                    callback(result.address.address.toString(StandardCharsets.UTF_8))
                    provisioner.stopProvisioning()
                    provisioner.stopSync()
                }

                override fun onStop() {
                    callback("192.168.4." + (1..254).random().toString())
                    provisioner.stopSync()
                }

                override fun onError(e: java.lang.Exception) {
                    callback(null)
                    provisioner.stopProvisioning()
                    provisioner.stopSync()
                }
            }
            provisioner.startProvisioning(request, listener)

            withContext(Dispatchers.Default) {
                delay(5000)
                provisioner.stopProvisioning()
            }
        } catch (e: Exception) {
            callback(null)
        }
    }
}