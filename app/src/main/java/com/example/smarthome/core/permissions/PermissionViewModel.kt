package com.example.smarthome.core.permissions

import android.Manifest
import com.example.smarthome.R
import com.example.smarthome.core.base.presentation.BaseViewModel

class PermissionViewModel() : BaseViewModel<PermissionState, Unit>() {

    private var requestPermission: Permission? = null

    fun init(requestPermission: Permission?) {
        this.requestPermission = requestPermission

        updateState { PermissionState.SetGrantResult(false) }

        when (requestPermission) {
            Permission.GEO -> {
                updateState {
                    PermissionState.SetTitleAndDescription(
                        R.string.permission_geo_title,
                        R.string.permission_geo
                    )
                }
                updateState { PermissionState.SetAnimation(R.raw.geo_permission_anim) }
            }
            Permission.GEO_LOGIN -> {
                updateState {
                    PermissionState.SetTitleAndDescription(
                        R.string.permission_geo_title,
                        R.string.permission_geo
                    )
                }
                updateState { PermissionState.SetAnimation(R.raw.geo_permission_anim) }
            }
            Permission.STORAGE -> {
                updateState {
                    PermissionState.SetTitleAndDescription(
                        R.string.permission_storage_title,
                        R.string.permission_storage
                    )
                }
                updateState { PermissionState.SetAnimation(R.raw.memory_permsission_anim) }
            }

            else -> updateState { PermissionState.Close }
        }
    }

    fun acceptPermission() {

        when (requestPermission) {
            Permission.GEO -> updateState {
                PermissionState.AskPermission(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }

            Permission.STORAGE -> updateState {
                PermissionState.AskPermission(
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                )
            }

            Permission.GEO_LOGIN -> updateState {
                PermissionState.AskGeoLoginPermission(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }

            else -> updateState { PermissionState.Close }
        }
    }

    fun onRequestCommonResult(result: Map<String, @JvmSuppressWildcards Boolean>) {
        val permission = result.keys.firstOrNull() ?: return
        val grantResult = result.getValue(permission)
        if (grantResult) {
            updateState { PermissionState.SetGrantResult(true) }
            updateState { PermissionState.Close }
        }
    }

    fun onRequestGeoResult() {
        updateState {
            PermissionState.StartLaunchActivity
        }
    }

    override fun createInitialState(): PermissionState {
        return PermissionState.SetGrantResult(false)
    }
}