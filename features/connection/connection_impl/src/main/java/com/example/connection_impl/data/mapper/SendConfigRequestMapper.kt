package com.example.connection_impl.data.mapper

import com.example.connection_impl.data.model.SendConfigRequest
import com.example.connection_impl.presentation.choose_device.recyclerView.model.WifiDevicesItem

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
