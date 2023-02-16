package com.example.smarthome.permissions

interface PermissionUiListener {
    fun onBack()
    fun onAcceptPermission(permissions: Array<String>?, tip : String)
}