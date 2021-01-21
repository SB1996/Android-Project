package com.santanu.android.androidapplication.utils.permission

import android.Manifest
import java.util.ArrayList

class PermissionList {
    companion object {
        const val permissionCode: Int = 10004
        val permissionList: Array<String> = arrayOf<String>(
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA
        )
    }
}