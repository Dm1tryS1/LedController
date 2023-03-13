package com.example.smarthome.fragments.information

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.app.NotificationCompat
import androidx.core.view.isVisible
import com.example.smarthome.R
import com.example.smarthome.core.base.presentation.BaseFragment
import com.example.smarthome.databinding.FragmentInformationBinding
import com.example.smarthome.fragments.information.dialog.*
import com.example.smarthome.fragments.information.recyclerView.adapter.InformationAdapter
import com.example.smarthome.fragments.information.recyclerView.model.InfoViewItem
import com.example.smarthome.common.device.SensorType
import com.example.smarthome.core.utils.supportBottomSheetScroll
import org.koin.androidx.viewmodel.ext.android.viewModel

class InformationFragment : BaseFragment<InformationState, InformationEvent>() {

    private lateinit var binding: FragmentInformationBinding
    override val vm: InformationViewModel by viewModel()
    private val adapter =
        InformationAdapter(onMenuClicked = { type, id, info, date ->
            vm.onMenuClicked(
                type,
                id,
                info,
                date
            )
        }, onDeviceClicked = { type, id -> vm.onChartOpen(type, id) })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInformationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        sensors.adapter = adapter
        sensors.supportBottomSheetScroll()

        reloadButton.setOnClickListener {
            vm.getInfo()
        }

        settings.setOnClickListener {
            vm.onSettingsClicked()
        }

        vm.getInfo()
    }

    private fun makeNotification(id: Int, text: String) {
        val builder = NotificationCompat.Builder(requireContext(), CHANEL_ID)
            .setSmallIcon(R.drawable.ic_smart_home)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(text)
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle().bigText(text))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager =
            requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANEL_ID,
                    CHANEL_ID,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }

        notificationManager.notify(id, builder.build())
    }

    override fun onBackPressed(): Boolean {
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        return super.onBackPressed()
    }

    override fun renderState(state: InformationState) {
        if (state.data != null) {
            binding.sensors.isVisible = true

            var currentType = SensorType.Unknown
            val items = mutableListOf<InfoViewItem>()
            state.data.forEach {
                if (currentType.type != it.sensorType.type) {
                    currentType = it.sensorType
                    items.add(InfoViewItem.Header(it.sensorType.text))
                }
                items.add(it)
            }
            adapter.items = items
            binding.loader.isVisible = state.progressVisibility

            if(!state.progressVisibility && items.isEmpty()) {
                binding.icon.isVisible = true
                binding.title.isVisible = true
            } else {
                binding.icon.isVisible = false
                binding.title.isVisible = false
            }
        } else {
            binding.sensors.isVisible = false
            binding.icon.isVisible = true
            binding.title.isVisible = true
        }
    }

    override fun handleEvent(event: InformationEvent) {
        when (event) {
            is InformationEvent.OpenSensorMenuEvent -> {
                Sensor.create(
                    fragment = this@InformationFragment,
                    action = event.command,
                    resources = event.resources,
                    data = event.data,
                    date = event.date
                ).show()
            }
            is InformationEvent.OpenConditionerMenuEvent -> {
                Conditioner.create(
                    fragment = this@InformationFragment,
                    action = event.command,
                    id = event.id,
                    on = event.on
                ).show()
            }
            is InformationEvent.OpenHumidifierMenuEvent -> {
                Humidifier.create(
                    fragment = this@InformationFragment,
                    action = event.command,
                    id = event.id,
                    on = event.on
                ).show()
            }
            is InformationEvent.OpenSettingsMenuEvent -> {
                Settings.create(
                    fragment = this@InformationFragment,
                    action = event.setTimer,
                    progress = event.value,
                    save = vm::saveUserSettings,
                    openSystemSettings = vm::onMoreSettings
                ).show()
            }
            is InformationEvent.ShowNotification -> {
                val text = if (event.type == SensorType.TemperatureSensor.type) {
                    if (event.more) {
                        getString(R.string.notification_max_temp, event.id, event.comfortableValue)
                    } else {
                        getString(R.string.notification_min_temp, event.id, event.comfortableValue)
                    }
                } else {
                    if (event.more) {
                        getString(R.string.notification_max_hum, event.id, event.comfortableValue)
                    } else {
                        getString(R.string.notification_min_hum, event.id, event.comfortableValue)
                    }
                }
                makeNotification(event.id, text)
            }
        }
    }

    companion object {
        const val CHANEL_ID = "CHANNEL_ID"
    }
}