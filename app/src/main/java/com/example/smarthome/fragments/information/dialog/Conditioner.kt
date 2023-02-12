package com.example.smarthome.fragments.information.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.smarthome.databinding.DropmenuConditioenerBinding
import com.example.smarthome.utils.BottomSheetDialogBuilder
import com.example.smarthome.common.device.Command

object Conditioner {
    fun create(
        fragment: Fragment,
        action: (aPackage: Command) -> Unit,
        ): Dialog {
        val binding = DropmenuConditioenerBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)


            offOn.setOnClickListener {
                action(Command.ConditionerOnOff)
            }

            reduce.setOnClickListener {
                action(Command.ConditionerReduceTemperature)
            }

            add.setOnClickListener {
               action(Command.ConditionerAddTemperature)
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}