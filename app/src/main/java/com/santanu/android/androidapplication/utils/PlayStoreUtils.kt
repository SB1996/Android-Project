package com.santanu.android.androidapplication.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.santanu.android.androidapplication.R

class PlayStoreUtils {

    fun openPlayStoreForApp(context: Context) {
        val appPackageName: String = context.packageName
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(
                        context.resources.getString(R.string.app_market_link) + appPackageName
                    )
                )
            )
        } catch (e: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(
                        context.resources.getString(R.string.app_google_play_store_link
                    ).toString() + appPackageName)
                )
            )
        }
    }
}