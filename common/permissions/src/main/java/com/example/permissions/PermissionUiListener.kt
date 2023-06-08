package com.example.permissions

interface PermissionUiListener {
    fun onBack()
    fun onAcceptPermission(permissions: Array<String>?, tip : String)
}