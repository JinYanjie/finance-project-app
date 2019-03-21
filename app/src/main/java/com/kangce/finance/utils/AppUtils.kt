package com.kangce.finance.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.telephony.TelephonyManager

object AppUtils {
    fun getDeviceId(context: Context): String? {
        var deviceId: String? = null
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            deviceId = tm.deviceId
        }
        return deviceId
    }

}