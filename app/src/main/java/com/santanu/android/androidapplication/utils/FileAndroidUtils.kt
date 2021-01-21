package com.santanu.android.androidapplication.utils

import android.content.Context
import android.os.Environment

class FileAndroidUtils(private val mContext: Context) {

    private fun isExternalStorageReadOnly(): Boolean {
        val extStorageState: String = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState
    }

    private fun isExternalStorageAvailable(): Boolean {
        val extStorageState: String = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == extStorageState
    }
}