package com.example.ledcontroller.fragments.information.recyclerView

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.ledcontroller.R
import com.example.ledcontroller.databinding.ItemInfoBinding
import com.example.ledcontroller.fragments.information.data.Package
import kotlin.experimental.and

class InformationAdapter(private val context: Context, private val action: (id: Int) -> Unit) :
    RecyclerView.Adapter<InformationAdapter.InformationViewHolder>() {

    var info: List<Package> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class InformationViewHolder(
        val binding: ItemInfoBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InformationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInfoBinding.inflate(inflater, parent, false)
        return InformationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InformationViewHolder, position: Int) {
        val information = info[position]
        with(holder.binding) {
            Log.d("here", info.toString())
            numberAndData.text = information.id.toString()
                when (information.id) {
                    1 -> {
                        icon.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_temperature
                            )
                        )
                        if (information.info != null)
                            date.text = "${information.info} °C"
                        else
                            date.text = "-"
                        reduce.isVisible = false
                        add.isVisible = false

                        holder.itemView.setOnClickListener {
                            action(getTemperature)
                        }

                    }
                    2 -> {
                        icon.setImageDrawable(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.ic_conditioner
                            )
                        )
                        if (information.info != null)
                            if ((information.info!!.toByte() and 128.toByte()) == 0.toByte())
                                date.text = "Выключено"
                            else
                                date.text = "Включено: ${
                                    (information.info!!.toByte() and 63.toByte()).toString(16)
                                } °C"
                        else
                            date.text = "-"
                        reduce.setOnClickListener {
                            action(reduceConditioenerTemperature)
                        }
                        add.setOnClickListener {
                            action(addConditioenerTemperature)
                        }
                        holder.itemView.setOnClickListener {
                            action(offConditioener)
                        }

                        reduce.isVisible = true
                        add.isVisible = true
                    }
                }

        }
    }

    override fun getItemCount(): Int {
        return info.count()
    }

    companion object Commands {
        val getTemperature = 1
        val onConditioener = 10
        val offConditioener = 2
        val addConditioenerTemperature = 26
        val reduceConditioenerTemperature = 18
    }

}