package com.example.smarthome.permissions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.smarthome.R


object PermissionHelper {

    fun updateUi(
        requestPermission: Permission?,
        title: TextView,
        tvInfo: TextView,
        btnAccept: Button,
        lottie: LottieAnimationView,
        permissionUiListener: PermissionUiListener
    ) {
        when (requestPermission) {
            Permission.GEO -> {
                title.setText(R.string.permission_geo_title)
                tvInfo.setText(R.string.permission_geo)
                lottie.setAnimation(R.raw.geo_permission_anim)
                btnAccept.setOnClickListener {
                    permissionUiListener.onAcceptPermission(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ), "Geo"
                    )
                }
            }
            Permission.GEO_LOGIN -> {
                title.setText(R.string.permission_geo_title)
                tvInfo.setText(R.string.permission_geo)
                lottie.setAnimation(R.raw.geo_permission_anim)
                btnAccept.setOnClickListener {
                    permissionUiListener.onAcceptPermission(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ), requestPermission.name
                    )
                }
            }
            Permission.STORAGE -> {
                title.setText(R.string.permission_storage_title)
                tvInfo.setText(R.string.permission_storage)
                lottie.setAnimation(R.raw.memory_permsission_anim)
                btnAccept.setOnClickListener {
                    permissionUiListener.onAcceptPermission(
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ), "Storage"
                    )
                }
            }
            else -> {
                permissionUiListener.onBack()
            }
        }
        lottie.repeatCount = LottieDrawable.INFINITE
        lottie.playAnimation()
    }

    fun neverAskAgainSelected(activity: Activity, permissions: Array<String>): Boolean {
        var tempFlag = false
        permissions.forEach { permission ->
            val prevShouldShowStatus: Boolean = getRatinaleDisplayStatus(activity, permission)
            val currShouldShowStatus = activity.shouldShowRequestPermissionRationale(permission!!)
            tempFlag = prevShouldShowStatus != currShouldShowStatus
            if (tempFlag) {
                return@forEach
            }
        }
        return tempFlag
    }

    fun getRatinaleDisplayStatus(context: Context, permission: String?): Boolean {
        val genPrefs: SharedPreferences =
            context.getSharedPreferences("GENERIC_PREFERENCES", Context.MODE_PRIVATE)
        return genPrefs.getBoolean(permission, false)
    }

    fun setShouldShowStatus(context: Context, permissions: ArrayList<String>?) {
        permissions?.forEach { permission ->
            val genPrefs = context.getSharedPreferences("GENERIC_PREFERENCES", Context.MODE_PRIVATE)
            val editor = genPrefs.edit()
            editor.putBoolean(permission, true)
            editor.apply()
        }
    }

    fun createPermissionList(permission: Permission): Array<String> {
        when (permission) {
            Permission.MIC -> {
                return arrayOf(Manifest.permission.RECORD_AUDIO)
            }
            Permission.GEO -> {
                return arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            }
            Permission.STORAGE -> {
                return arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )

            }
            Permission.CAMERA -> {
                return arrayOf(
                    Manifest.permission.CAMERA,
                )
            }
            else -> {
                return emptyArray()
            }
        }
    }

    fun hasPermissions(
        context: Context?,
        permissions: Array<String>
    ): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission.toString()
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

}