package com.santanu.android.application.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.santanu.android.androidapplication.ui.activity.MainActivity
import com.santanu.android.application.R

class SplashActivity : AppCompatActivity() {
    private val TAG: String = SplashActivity::class.java.simpleName

    companion object {
        private const val SPLASH_TIME_OUT = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        @Suppress("DEPRECATION")
        Handler().postDelayed({
            val intentMain = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intentMain)
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }
}