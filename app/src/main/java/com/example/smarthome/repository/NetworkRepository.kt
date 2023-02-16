package com.example.smarthome.repository

import android.util.Log
import com.example.smarthome.repository.model.BaseResponse
import com.example.smarthome.repository.model.SendConfig
import kotlinx.coroutines.delay

class NetworkRepository {

    suspend fun sendConfig(systemIp: String, data: List<Pair<String, Int>>, callback: (BaseResponse<SendConfig>) -> Unit) {
        Log.d("here","system ${systemIp} ${data} ${data}}")
        delay(2000)
        callback(BaseResponse(null, SendConfig()))
    }
}