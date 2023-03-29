package com.example.smarthome.fragments.connectDevice.chooseDevice.recyclerView.adapter

import androidx.core.content.ContextCompat
import com.example.smarthome.R
import com.example.data.device.SensorType
import com.example.core.utils.AdapterUtil
import com.example.core.utils.adapterDelegateViewBinding
import com.example.smarthome.databinding.ItemWifiDeviceBinding
import com.example.smarthome.fragments.connectDevice.chooseDevice.recyclerView.model.WifiDevicesItem
import com.example.core.utils.bindWithBinding
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter


class WifiDeviceAdapter(onItemClicked: (type: Int, id: Int) -> Unit) :
    AsyncListDifferDelegationAdapter<WifiDevicesItem>(
        AdapterUtil.diffUtilItemCallbackEquals(),
        AdapterUtil.adapterDelegatesManager(
            createParticipantsAdapter(onItemClicked)
        )
    )

fun createParticipantsAdapter(onItemClicked: (type: Int, id: Int) -> Unit) =
    adapterDelegateViewBinding<WifiDevicesItem, ItemWifiDeviceBinding>(
        ItemWifiDeviceBinding::inflate
    ) {

        binding.root.setOnClickListener {
            onItemClicked(item.deviceType, item.id)
        }

        bindWithBinding {
            icon.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    if (item.deviceType == SensorType.Conditioner.type) {
                        R.drawable.ic_conditioner
                    } else {
                        R.drawable.ic_humidifier
                    }
                )
            )
            name.text = "${item.brand} ${item.name}"
        }
    }

