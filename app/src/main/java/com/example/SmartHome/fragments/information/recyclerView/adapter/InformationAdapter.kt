package com.example.smarthome.fragments.information.recyclerView.adapter

import androidx.core.content.ContextCompat
import com.example.smarthome.databinding.ItemInfoBinding
import com.example.smarthome.fragments.information.recyclerView.model.InfoViewItem
import com.example.smarthome.utils.AdapterUtil
import com.example.smarthome.utils.adapterDelegateViewBinding
import com.example.smarthome.utils.bindWithBinding
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter


class InformationAdapter(onMenuClicked: (Int) -> Unit) :
    AsyncListDifferDelegationAdapter<InfoViewItem>(
        AdapterUtil.diffUtilItemCallbackEquals(),
        AdapterUtil.adapterDelegatesManager(
            createParticipantsAdapter(onMenuClicked)
        )
    )

fun createParticipantsAdapter(onMenuClicked: (Int) -> Unit) =
    adapterDelegateViewBinding<InfoViewItem, ItemInfoBinding>(
        ItemInfoBinding::inflate
    ) {
        binding.dropdownMenu.setOnClickListener {
            onMenuClicked(item.id)
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
