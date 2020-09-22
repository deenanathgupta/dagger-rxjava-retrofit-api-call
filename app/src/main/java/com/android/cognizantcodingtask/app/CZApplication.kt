package com.android.cognizantcodingtask.app

import android.content.Context
import androidx.multidex.MultiDex
import com.android.cognizantcodingtask.BuildConfig
import com.android.cognizantcodingtask.di.component.DaggerAppComponent
import com.android.cognizantcodingtask.network.RestApiClient
import com.android.cognizantcodingtask.network.RestConstants
import com.android.cognizantcodingtask.utils.CZConstants
import com.android.cognizantcodingtask.utils.Logger
import com.android.cognizantcodingtask.utils.UtilsHelper
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import org.json.JSONException
import org.json.JSONObject


class CZApplication : DaggerApplication() {
    init {
        instance = this
    }

    companion object {
        private var instance: CZApplication? = null
        var account_token: String? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun getBaseUrl(): String {
            return CZConstants.BASE_URL
        }

        fun getUserAgent(): String {
            try {
                val userAgent = JSONObject()
                userAgent.put(RestConstants.KEY_PLATFORM, RestConstants.VALUE_ANDROID)
                userAgent.put(RestConstants.KEY_VERSION, BuildConfig.VERSION_NAME)
                userAgent.put(RestConstants.KEY_DEVICE_ID, UtilsHelper.getDeviceId())
                userAgent.put(RestConstants.KEY_MODEL, android.os.Build.MANUFACTURER.toUpperCase())
                userAgent.put(RestConstants.KEY_OS_VERSION, android.os.Build.VERSION.SDK_INT)
                Logger.d(userAgent.toString())
                return userAgent.toString()
            } catch (e: JSONException) {
                return RestConstants.VALUE_ANDROID
            }

        }

    }

    override fun onCreate() {
        super.onCreate()
        initSetup()
    }

    private fun initSetup() {
        setUpClient()
    }

    override fun applicationInjector(): AndroidInjector<out CZApplication> {
        return DaggerAppComponent.builder().create(this);
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    /**
     * Custom client creation code this is just example please pass your exact values
     * Pass reset client value to true if at some point you want to create new instance of client
     */
    private fun setUpClient() {
        RestApiClient.ApiClientBuilder()
                .baseUrl(getBaseUrl())
                .resetClient(false)
                .build()
    }


}