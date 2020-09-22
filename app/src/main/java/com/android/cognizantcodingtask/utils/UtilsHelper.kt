package com.android.cognizantcodingtask.utils

import android.app.Activity
import android.content.Context
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import com.android.cognizantcodingtask.app.CZApplication


class UtilsHelper {

    companion object {

        fun hideKeyboard(context: Context?) {
            if (context == null) return
            val activity = context as Activity?
            val inputMethodManager = activity!!.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            if (inputMethodManager != null && !activity.isFinishing && activity.currentFocus != null)
                inputMethodManager.hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken, 0
                )
        }
        fun getDeviceId(): String {
            return Settings.Secure.getString(CZApplication.applicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID)
        }

    }
}