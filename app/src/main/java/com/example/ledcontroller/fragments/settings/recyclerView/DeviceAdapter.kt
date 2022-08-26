package com.example.ledcontroller.fragments.settings.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ledcontroller.fragments.settings.data.Device
import com.example.ledcontroller.databinding.ItemDeviceBinding

class DeviceAdapter(private val action : (address: String) -> Unit) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    var devices: List<Device> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class DeviceViewHolder(
        val binding:ItemDeviceBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDeviceBinding.inflate(inflater, parent, false)
        return DeviceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = devices[position]
        with(holder.binding) {
            address.text = device.address
            name.text = device.name
        }
        holder.itemView.setOnClickListener {
            action(device.address)
        }
    }

    override fun getItemCount(): Int {
        return devices.count()
    }
}