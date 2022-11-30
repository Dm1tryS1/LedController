package com.example.smarthome.fragments.home

sealed class HomeEvent {
    object NoConnectionEvent : HomeEvent()
}