package com.santanu.android.androidapplication.data.services

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushMessagingService : FirebaseMessagingService() {
    private val TAG: String = PushMessagingService::class.java.simpleName

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "PushMessagingService{} : onMessageReceived() >>" +
            " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: ${remoteMessage.notification?.body}"
        )

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "PushMessagingService{} : onNewToken() >>" +
            " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: Push Notification Token : $token"
        )
    }
}