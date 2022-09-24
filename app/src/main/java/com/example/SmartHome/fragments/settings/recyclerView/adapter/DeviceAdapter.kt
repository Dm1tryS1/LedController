package com.example.SmartHome.fragments.settings.recyclerView.adapter

import com.example.SmartHome.databinding.ItemDeviceBinding
import com.example.SmartHome.fragments.settings.recyclerView.model.DeviceViewItem
import com.example.SmartHome.utils.AdapterUtil
import com.example.SmartHome.utils.adapterDelegateViewBinding
import com.example.SmartHome.utils.bindWithBinding
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class DeviceAdapter(onDeviceClicked: (address: String) -> Unit) :
    AsyncListDifferDelegationAdapter<DeviceViewItem>(
        AdapterUtil.diffUtilItemCallbackEquals(),
        AdapterUtil.adapterDelegatesManager(
            createParticipantsAdapter(onDeviceClicked)
        )
    )

fun createParticipantsAdapter(onMenuClicked: (address: String) -> Unit) =
    adapterDelegateViewBinding<DeviceViewItem, ItemDeviceBinding>(
        ItemDeviceBinding::inflate
    ) {
        binding.root.setOnClickListener {
            onMenuClicked(item.address)
        }

        bindWithBinding {
            address.text = item.address
            name.text = item.name
        }
    }
