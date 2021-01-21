package com.santanu.android.androidapplication.utils.notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.santanu.android.androidapplication.R
import com.santanu.android.androidapplication.ui.activity.MainActivity

class NotificationUtils(private val mApplication: Application) {
    private val TAG: String = NotificationUtils::class.java.simpleName

    companion object {
        internal const val NOTIFICATION_ID: Int = 10001
    }

    private var mNotificationManager: NotificationManager? = null
    private var mNotificationImportance: Int = NotificationManager.IMPORTANCE_DEFAULT
        set(value) {
            field = value
        }

    private var mNotificationChannelId: String = mApplication.resources.getString(R.string.default_notification_channel_id)
        set(value) {
            field = value
        }

    private var mDesActivity: Class<MainActivity> = MainActivity::class.java
        set(value) {
            field = value
        }

    init {
        mNotificationManager = mApplication.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelID: String, name: String, description: String) {

        val channel: NotificationChannel = NotificationChannel(channelID, name, mNotificationImportance).apply {
            this.description = description
            this.setShowBadge(true)
            this.importance = mNotificationImportance
            this.enableLights(true)
            this.lightColor = Color.RED
            this.enableVibration(true)
            this.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            this.setSound(null, null)
        }

        mNotificationManager?.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun deleteNotificationChannel(channelID: String) {
        mNotificationManager?.deleteNotificationChannel(channelID)
    }

    internal fun sendNotification(view: View) {
        val intent: Intent = Intent(mApplication.applicationContext, mDesActivity).apply {
            this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            mApplication.applicationContext, 0, intent, 0
        )

        val notification: Notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(mApplication.applicationContext, mNotificationChannelId)
                .setContentTitle("Example Notification")
                .setContentText("This is an example notification.")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setChannelId(mNotificationChannelId)
                .setContentIntent(pendingIntent)
                .setNumber(3)
                .build()
        } else {
            NotificationCompat.Builder(mApplication.applicationContext, mNotificationChannelId)
                .setContentTitle("Example Notification")
                .setContentText("This is an  example notification.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentIntent(pendingIntent)
                .setNumber(3)
                .build()
        }

        mNotificationManager?.notify(NOTIFICATION_ID, notification)
    }

}