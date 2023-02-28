package com.example.smarthome.fragments.information.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.smarthome.databinding.DropmenuConditioenerBinding
import com.example.smarthome.core.utils.BottomSheetDialogBuilder
import com.example.smarthome.common.device.Command
import com.example.smarthome.common.device.CommandForConditioner

object Conditioner {
    fun create(
        fragment: Fragment,
        action: ((aPackage: Command) -> Unit)?,
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
                    action?.invoke(Command.ConditionerCommand(id, CommandForConditioner.On))
                } else {
                    action?.invoke(Command.ConditionerCommand(id, CommandForConditioner.Off))
                }
                dialog.dismiss()
            }

            reduce.setOnClickListener {
                action?.invoke(Command.ConditionerCommand(id, CommandForConditioner.ReduceTemperature))
            }

            add.setOnClickListener {
                action?.invoke(Command.ConditionerCommand(id, CommandForConditioner.AddTemperature))
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}