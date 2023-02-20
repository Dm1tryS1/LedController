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
        action: (aPackage: Command) -> Unit,
        id: Int
        ): Dialog {
        val binding = DropmenuConditioenerBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)


            offOn.setOnClickListener {
                action(Command.ConditionerCommand(id, CommandForConditioner.OnOff))
            }

            reduce.setOnClickListener {
                action(Command.ConditionerCommand(id, CommandForConditioner.ReduceTemperature))
            }

            add.setOnClickListener {
               action(Command.ConditionerCommand(id, CommandForConditioner.AddTemperature))
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}