package com.example.smarthome.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.smarthome.R
import com.example.smarthome.common.navigation.BackPressConsumer
import com.example.smarthome.databinding.ActivityMainBinding
import com.example.smarthome.service.WiFiService
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val fragmentContainer = R.id.fragment_container

    private val navigatorCicerone by lazy { AppNavigator(this, fragmentContainer) }
    private val navigatorHolder: NavigatorHolder by inject()
    private val router: Router by inject()

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        navigatorHolder.removeNavigator()
        navigatorHolder.setNavigator(navigatorCicerone)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigatorCicerone)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
        }

        startService(Intent(this, WiFiService::class.java))

        router.newRootScreen(Screens.MainScreen())
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(fragmentContainer)
        if (fragment is BackPressConsumer && fragment.onBackPressed()) {
            return
        }
        router.exit()
    }

}