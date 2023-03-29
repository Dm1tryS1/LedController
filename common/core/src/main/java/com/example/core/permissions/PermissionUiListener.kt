package com.example.core.permissions

interface PermissionUiListener {
    fun onBack()
    fun onAcceptPermission(permissions: Array<String>?, tip : String)
}