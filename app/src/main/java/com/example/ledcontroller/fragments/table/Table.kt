package com.example.ledcontroller.fragments.table

import android.os.Bundle
import android.text.method.MovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.example.ledcontroller.databinding.FragmentPixelTableBinding
import com.example.ledcontroller.fragments.table.data.Drawing
import com.example.ledcontroller.main.MainActivity
import com.example.ledcontroller.main.Screen
import org.koin.androidx.viewmodel.ext.android.viewModel

class Table : Fragment() {
    private lateinit var binding: FragmentPixelTableBinding
    private val vm: TableViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPixelTableBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tableBack.setOnClickListener {
            (activity as MainActivity).navigate(Screen.Home)
        }

        binding.tableReload.setOnClickListener {
            binding.pixelTable.forEach { layout ->
                (layout as LinearLayout).forEach {
                    (it as CheckBox).isChecked = false
                }
            }
        }

        binding.pixelTable.let {
            var tag = 0
            it.forEach { layout ->
                (layout as LinearLayout).forEach { view ->
                    (view as CheckBox).tag = tag
                    tag++
                    view.setOnClickListener { v ->

                        if ((v as CheckBox).isChecked)
                            vm.sendDataForDrawing(Drawing(1, v.tag.toString().toInt(), 0))
                        else
                            vm.sendDataForDrawing(Drawing(0, v.tag.toString().toInt(), 0))

                        Log.d("isChecked", v.isChecked.toString())
                        Log.d("tag", v.tag.toString())

                    }
                }
            }
        }
    }
}
