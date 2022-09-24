package com.example.ledcontroller.fragments.information.dialog

import android.app.Dialog
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.example.ledcontroller.databinding.DropmenuSettingsBinding
import com.example.ledcontroller.utils.BottomSheetDialogBuilder
import kotlin.experimental.or

object Settings {
    fun create(
        fragment: Fragment,
        action: (aPackage: Pair<Int, Int>) -> Unit,
        save: (value: Int) -> Unit,
        progress: Int = 0
    ): Dialog {
        val binding = DropmenuSettingsBinding.inflate(fragment.layoutInflater)

        with(binding) {

            val dialog = BottomSheetDialogBuilder(fragment)
                .addCustomView(root)
                .setCancelable(true)

            close.setOnClickListener {
                dialog.dismiss()
            }


            date.progress = progress
            minute.text = if (progress == 0)
                "Выключено"
            else
                (progress*5).toString()

            date.setOnSeekBarChangeListener(Listener(binding))

            update.setOnClickListener {
                action(
                    Pair(
                        0x00,
                        ((date.progress * 5).toByte() or 128.toByte()).toInt()
                    )
                )
                save(date.progress)
                dialog.dismiss()
            }

            return dialog.build()
        }
    }

    class Listener(val binding: DropmenuSettingsBinding) : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            if (p1 != 0)
                binding.minute.text = (p1 * 5).toString()
            else
                binding.minute.text = "Выключено"
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {

        }

        override fun onStopTrackingTouch(p0: SeekBar?) {

        }
    }
}