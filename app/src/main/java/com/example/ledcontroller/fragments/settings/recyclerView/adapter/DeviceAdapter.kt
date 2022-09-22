package com.example.ledcontroller.fragments.settings.recyclerView.adapter

import com.example.ledcontroller.databinding.ItemDeviceBinding
import com.example.ledcontroller.fragments.settings.recyclerView.model.DeviceViewItem
import com.example.ledcontroller.utils.AdapterUtil
import com.example.ledcontroller.utils.adapterDelegateViewBinding
import com.example.ledcontroller.utils.bindWithBinding
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
