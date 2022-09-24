package com.example.smarthome.main

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.smarthome.R
import com.example.smarthome.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navigator: HomeNavigator
    private var permissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigator(savedInstanceState?.getInt("id"))
    }

    private fun initNavigator(selectedId: Int?) {
        navigator = HomeNavigator(supportFragmentManager, R.id.nav_host_fragment_activity_main)
        binding.navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> navigate(Screen.Home)
                R.id.settings -> navigate(Screen.Settings)
                else -> {
                    Log.d("Navigator", "Something went wrong")
                    return@setOnItemSelectedListener false
                }
            }
            return@setOnItemSelectedListener true
        }

        if (selectedId != null)
            binding.navView.selectedItemId = selectedId
        navigator.setScreen(Screen.Home)

    }

    fun navigate(screen: Screen) {
        navigator.setScreen(screen)
    }

    override fun onResume() {
        super.onResume()
        if (!permissionGranted)
            getLocationPermission()
    }


    private fun getLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            checkSelfPermission()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun checkSelfPermission() {
        val accessFineLocation = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.BLUETOOTH
        )
        if (accessFineLocation != PackageManager.PERMISSION_GRANTED)
            requestMultiplePermissions.launch(
                arrayOf(
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.BLUETOOTH
                )
            )
    }

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissionGranted = true
            permissions.entries.forEach {
                if (!it.value) permissionGranted = false
            }
            if (!permissionGranted) {
                startDeniedPermissionAlert()
            }
        }

    private fun startDeniedPermissionAlert() {
        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this).apply {
            setTitle("Permission needed")
            setMessage("Please, Allow all the time location permission for app to work")
            setPositiveButton("Open Setting") { _, _ ->
                val uri: Uri = Uri.fromParts("package", packageName, null)
                val intent = Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = uri
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
                }
                startActivity(intent)
            }
            setNegativeButton("Quit") { _, _ ->
                startActivity(Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME))
            }
        }

        val dialog: AlertDialog = alertDialogBuilder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

        val mPositiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        mPositiveButton.setOnClickListener {
            dialog.cancel()
            val uri: Uri = Uri.fromParts("package", packageName, null)
            val intent = Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = uri
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            }
            startActivity(intent)
        }
    }
}