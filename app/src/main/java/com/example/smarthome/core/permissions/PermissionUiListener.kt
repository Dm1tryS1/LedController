package com.example.smarthome.core.permissions

interface PermissionUiListener {
    fun onBack()
    fun onAcceptPermission(permissions: Array<String>?, tip : String)
}