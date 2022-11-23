package com.example.smarthome.utils

import android.content.res.Resources
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.example.smarthome.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun View.showSnack(
    text: String,
    actionText: String? = null,
    actionClick: View.OnClickListener? = null,
    dismissed: ((Unit) -> Unit)? = null,
) {
    val snackBar = Snackbar.make(this, text, Snackbar.LENGTH_SHORT)

    snackBar.view.apply {
        findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setTextAppearance(R.style.text)
            }
            textAlignment = View.TEXT_ALIGNMENT_CENTER
        }

        val params = layoutParams as ViewGroup.MarginLayoutParams
        val margin = 8.dp

        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        params.setMargins(margin, margin, margin, 10.dp)

        layoutParams = params
        translationZ = 100F
        background = AppCompatResources.getDrawable(context, R.drawable.bg_snackbar)

        ViewCompat.setElevation(this, 2.dp.toFloat())
    }

    if (actionText != null) {
        snackBar.setAction(actionText, actionClick)
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        snackBar.setActionTextColor(context.getColor(R.color.blue))
    }
    snackBar.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
        override fun onShown(transientBottomBar: Snackbar?) {
            super.onShown(transientBottomBar)
        }

        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            super.onDismissed(transientBottomBar, event)
            dismissed?.invoke(Unit)
        }
    })

    snackBar.show()
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun Fragment.snackBar(text: String) {
    requireActivity().window.decorView.findViewById<View>(android.R.id.content).showSnack(text)
}