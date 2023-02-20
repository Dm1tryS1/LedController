package com.example.smarthome.core.permissions

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes

sealed class PermissionState {
    class SetTitleAndDescription(@StringRes val titleId: Int, @StringRes val descriptionId: Int) :
        PermissionState()
    class SetAnimation(@RawRes val id: Int) : PermissionState()
    class AskPermission(val permissions: Array<String>) : PermissionState()
    class AskGeoLoginPermission(val permissions: Array<String>) : PermissionState()
    object Close : PermissionState()
    class SetGrantResult(val result: Boolean) : PermissionState()
    object StartLaunchActivity : PermissionState()
}