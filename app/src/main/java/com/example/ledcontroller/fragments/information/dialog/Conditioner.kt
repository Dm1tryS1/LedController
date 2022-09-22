package com.example.ledcontroller.fragments.information.dialog

import android.app.Dialog
import androidx.fragment.app.Fragment
import com.example.ledcontroller.databinding.DropmenuConditioenerBinding
import com.example.ledcontroller.utils.BottomSheetDialogBuilder

object Conditioner {
    fun create(
        fragment: Fragment,
        action: (command: Int) -> Unit,
        commandOffOn: Int,
        commandAdd: Int,
        commandReduce: Int,
        ): Dialog {
        val binding = DropmenuConditioenerBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)


            offOn.setOnClickListener {
                action(commandOffOn)
            }

            reduce.setOnClickListener {
                action(commandReduce)
            }

            add.setOnClickListener {
                action(commandAdd)
            }

            close.setOnClickListener {
                dialog.dismiss()
            }

            return dialog.build()
        }
    }
}