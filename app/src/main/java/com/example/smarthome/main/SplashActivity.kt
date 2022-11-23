package com.example.smarthome.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.smarthome.R
import com.example.smarthome.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animation = AnimationUtils.loadAnimation(this, R.anim.anim_launch)
        animation.duration = 2000
        animation.fillAfter = true
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {

                startActivity(Intent(this@SplashActivity,MainActivity::class.java))
                overridePendingTransition(org.koin.android.R.anim.abc_grow_fade_in_from_bottom, org.koin.android.R.anim.abc_fade_in)
                this@SplashActivity.finish()
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })

        binding.icon.startAnimation(animation)


    }
}