package com.example.information_impl.data.mapper

import com.example.data.DeviceInfoSchema
import com.example.data.Time
import com.example.information_impl.data.model.GetAllResponse


fun getAllResponseMapper(
    response: GetAllResponse,
    time: Time
): List<DeviceInfoSchema> {
    val list = mutableListOf<DeviceInfoSchema>()
    response.temperature?.let { temperatureList ->
        temperatureList.forEach { temperature ->
            temperature?.let {
                list.add(
                    temperatureResponseMapper(
                        it,
                        time
                    )
                )
            }
        }
    }

    response.humidity?.let { humidityList ->
        humidityList.forEach { humidity ->
            humidity?.let {
                list.add(
                   humidityResponseMapper(it, time)
                )
            }
        }
    }

    response.pressure?.let { pressureList ->
        pressureList.forEach { pressure ->
            pressure?.let {
                list.add(
                    pressureResponseMapper(it, time)
                )
            }
        }
    }

    response.conditioner?.let { list.add(
        conditionerResponseMapper(
            it,
            time
        )
    ) }
    response.humidifier?.let { list.add(
        humidifierResponseMapper(
            it,
            time
        )
    ) }
    return list.toList()
}