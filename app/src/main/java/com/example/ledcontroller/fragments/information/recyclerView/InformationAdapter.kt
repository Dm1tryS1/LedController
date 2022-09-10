package com.example.ledcontroller.fragments.information.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ledcontroller.databinding.ItemInfoBinding
import com.example.ledcontroller.fragments.information.data.Data

class InformationAdapter() : RecyclerView.Adapter<InformationAdapter.InformationViewHolder>() {

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
            date.text = information.info
        }
    }

    override fun getItemCount(): Int {
        return info.count()
    }
}