package com.example.ledcontroller.fragments.information.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ledcontroller.R
import com.example.ledcontroller.databinding.ItemInfoBinding
import com.example.ledcontroller.fragments.information.data.Data

class InformationAdapter(private val context: Context, private val action: (id: Int) -> Unit) :
    RecyclerView.Adapter<InformationAdapter.InformationViewHolder>() {

    var info: List<Data> = emptyList()
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
                        date.text = information.info + " °C"
                    else
                        date.text = "-"
                }
                2 -> {
                    icon.setImageDrawable(
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.ic_conditioner
                        )
                    )
                    if (information.info != null)
                        if (information.info.toInt() == 2)
                            date.text = "Включено"
                        else
                            date.text = "Выключено"
                    else
                        date.text = "-"
                }
            }

            holder.itemView.setOnClickListener {
                action(information.id)
            }

        }
    }

    override fun getItemCount(): Int {
        return info.count()
    }

}