package com.example.smarthome.fragments.information.recyclerView.adapter

import androidx.core.content.ContextCompat
import com.example.smarthome.databinding.ItemDeviceSeparatorBinding
import com.example.smarthome.databinding.ItemInfoBinding
import com.example.smarthome.fragments.information.recyclerView.model.InfoViewItem
import com.example.smarthome.utils.AdapterUtil
import com.example.smarthome.utils.adapterDelegateViewBinding
import com.example.smarthome.utils.bindWithBinding
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter


class InformationAdapter(onMenuClicked: (Int, Int, String, String) -> Unit, onDeviceClicked: (Int, Int) -> Unit) :
    AsyncListDifferDelegationAdapter<InfoViewItem>(
        AdapterUtil.diffUtilItemCallbackEquals(),
        AdapterUtil.adapterDelegatesManager(
            createParticipantsAdapter(onMenuClicked, onDeviceClicked),
            createHeaderAdapter()
        )
    )

fun createParticipantsAdapter(onMenuClicked: (Int, Int, String, String) -> Unit, onDeviceClicked: (Int, Int) -> Unit) =
    adapterDelegateViewBinding<InfoViewItem.SensorsInfoViewItem, ItemInfoBinding>(
        ItemInfoBinding::inflate
    ) {
        binding.dropdownMenu.setOnClickListener {
            onMenuClicked(item.sensorType.type, item.id, item.info, item.date)
        }

        binding.root.setOnClickListener {
            onDeviceClicked(item.sensorType.type, item.id)
        }

        bindWithBinding {
            icon.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    item.iconId
                )
            )
            name.text = item.info
        }
    }

fun createHeaderAdapter() =
    adapterDelegateViewBinding<InfoViewItem.Header, ItemDeviceSeparatorBinding>(
        ItemDeviceSeparatorBinding::inflate
    ) {
        bindWithBinding {
            type.text = item.type
        }
    }