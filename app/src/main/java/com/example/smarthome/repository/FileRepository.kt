package com.example.smarthome.repository

import android.content.Context
import com.example.smarthome.fragments.connectDevice.chooseDevice.recyclerView.model.WifiDevicesItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets

class FileRepository(private val context: Context) {

    fun getJSONFromFile(path: String): String? {

        val jsonString: String = try {
            val `is`: InputStream = context.assets.open(path)
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, StandardCharsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }

    fun findDeviceConfig(ids: List<Int>): List<WifiDevicesItem> = (Gson().fromJson(
        getJSONFromFile(FileName),
        object : TypeToken<Map<String, WifiDevicesItem>>() {}.type
    ) as Map<String, WifiDevicesItem>).filter { ids.contains(it.value.id) }
        .map {
            it.value
        }

    companion object {
        const val FileName = "wifi_devices.json"
    }

}