package com.example.smarthome.core.permissions

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.airbnb.lottie.LottieAnimationView
import com.example.smarthome.R
import com.example.smarthome.core.permissions.PermissionHelper.hasPermissions
import com.example.smarthome.core.utils.createCenter
import com.example.smarthome.core.utils.showSnack


class PermissionActivity : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var tvInfo: TextView
    private lateinit var btnAccept: Button
    private lateinit var lottie: LottieAnimationView
    private lateinit var close: ImageButton
    private var requestPermission: Permission? = null
    private var requestPermissionList: ArrayList<String>? = null

    private val requestCommonPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val permission = result.keys.firstOrNull() ?: return@registerForActivityResult
        val grantResult = result.getValue(permission)
        if (grantResult) {
            PermissionHelper.setShouldShowStatus(this, requestPermissionList)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_permission)

        initViews()
        initArguments()
    }

    override fun onResume() {
        super.onResume()
        requestPermission?.let {
            val checkPermissions = PermissionHelper.createPermissionList(it)
            if (hasPermissions(this, checkPermissions)) {
                window.decorView.findViewById<View>(android.R.id.content).showSnack(getString(R.string.permission_already_permited))
                finish()
            }
        }
    }

    private fun initViews() {
        title = findViewById(R.id.title)
        lottie = findViewById(R.id.lottie)
        tvInfo = findViewById(R.id.tv_info)
        btnAccept = findViewById(R.id.btn_accept)
        close = findViewById(R.id.close)
        close.setOnClickListener { finish() }
    }

    private fun initArguments() {
        intent.extras?.let { args ->
            requestPermission =
                args.getSerializable(PermissionFragment.KEY_PERMISSION) as Permission?
        }

        PermissionHelper.updateUi(
            requestPermission,
            title,
            tvInfo,
            btnAccept,
            lottie,
            object : PermissionUiListener {
                override fun onBack() {
                    finish()
                }

                override fun onAcceptPermission(permissions: Array<String>?, tip: String) {
                    permissions?.let {
                        if (PermissionHelper.neverAskAgainSelected(
                                this@PermissionActivity,
                                permissions
                            )
                        ) {
                            displayNeverAskAgainDialog()
                        } else {
                            askPermission(permissions)
                            requestPermissions(permissions, 1)
                        }
                    }
                }
            })
    }

    fun askPermission(permissions: Array<String>) {
        requestCommonPermissionLauncher.launch(permissions)
    }

    private fun displayNeverAskAgainDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_never_ask_permission, null, false)
        val dialog = createCenter(dialogView)
        val accept = dialogView.findViewById<AppCompatButton>(R.id.accept)
        val cancel = dialogView.findViewById<AppCompatButton>(R.id.cancel)
        val dialogText = dialogView.findViewById<TextView>(R.id.dialog_text)
        accept.setOnClickListener {
            dialog.cancel()
            manualRequestPermission()
        }
        cancel.setOnClickListener {
            dialog.cancel()
        }
        if (requestPermission == Permission.STORAGE) {
            dialogText.setText(R.string.request_permission_never_ask_storage)
        } else {
            dialogText.setText(R.string.request_permission_never_ask)
        }
        dialog.show()
    }

    private fun manualRequestPermission() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.data = Uri.parse("package:$packageName")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        startActivity(intent)
    }

}