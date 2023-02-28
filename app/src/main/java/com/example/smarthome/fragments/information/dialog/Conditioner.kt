package com.example.smarthome.fragments.information.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.smarthome.databinding.DropmenuConditioenerBinding
import com.example.smarthome.core.utils.BottomSheetDialogBuilder
import com.example.smarthome.common.device.Command
import com.example.smarthome.common.device.CommandForConditioner
import com.example.smarthome.common.device.ConditionerCommands

object Conditioner {
    fun create(
        fragment: Fragment,
        action: (command: String) -> Unit,
        id: Int,
        on: Boolean
        ): Dialog {
        val binding = DropmenuConditioenerBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            offOn.setOnClickListener {
                if (on) {
                    action.invoke(ConditionerCommands.On.command)
                } else {
                    action.invoke(ConditionerCommands.Off.command)
                }
                dialog.dismiss()
            }

            reduce.setOnClickListener {
                action.invoke(ConditionerCommands.ReduceTemperature.command)
            }

            add.setOnClickListener {
                action.invoke(ConditionerCommands.AddTemperature.command)
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}