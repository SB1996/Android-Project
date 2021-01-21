package com.santanu.android.androidapplication.utils

import android.content.res.Resources
import kotlin.math.roundToInt

class DeviceUnitUtils {

    fun dpToPx(dp: Float): Int {
        val density = Resources.getSystem().displayMetrics.density
        return (dp * density).roundToInt()
    }

    fun pxToDp(px: Float): Float {
        val densityDpi = Resources.getSystem().displayMetrics.densityDpi.toFloat()
        return px / (densityDpi / 160f)
    }
}