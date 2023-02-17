package com.example.smarthome.service.network.mapper

import com.example.smarthome.fragments.connectDevice.chooseDevice.recyclerView.model.WifiDevicesItem
import com.example.smarthome.service.network.model.SendConfigRequest

fun sendConfigRequestMapper(
    wifiDevicesItem: WifiDevicesItem,
    ip: String
) = SendConfigRequest(
    ip = ip,
    id = wifiDevicesItem.id,
    brand = wifiDevicesItem.brand,
    name = wifiDevicesItem.name,
    commands = wifiDevicesItem.commands,
    deviceType = wifiDevicesItem.deviceType
)
