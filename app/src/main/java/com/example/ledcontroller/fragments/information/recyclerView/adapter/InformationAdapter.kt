package com.example.ledcontroller.fragments.information.recyclerView.adapter

import android.view.View
import androidx.core.content.ContextCompat
import com.example.ledcontroller.databinding.ItemInfoBinding
import com.example.ledcontroller.fragments.information.recyclerView.model.InfoViewItem
import com.example.ledcontroller.fragments.information.recyclerView.util.AdapterUtil
import com.example.ledcontroller.fragments.information.recyclerView.util.adapterDelegateViewBinding
import com.example.ledcontroller.fragments.information.recyclerView.util.bindWithBinding
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter


class InformationAdapter(onMenuClicked: (Int, View) -> Unit) :
    AsyncListDifferDelegationAdapter<InfoViewItem>(
        AdapterUtil.diffUtilItemCallbackEquals(),
        AdapterUtil.adapterDelegatesManager(
            createParticipantsAdapter(onMenuClicked)
        )
    )

fun createParticipantsAdapter(onMenuClicked: (Int, View) -> Unit) =
    adapterDelegateViewBinding<InfoViewItem, ItemInfoBinding>(
        ItemInfoBinding::inflate
    ) {
        binding.dropdownMenu.setOnClickListener {
            onMenuClicked(item.id,it)
        }

        bindWithBinding {
            icon.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    item.iconId
                )
            )
            name.text = item.date
        }
    }
