package com.santanu.android.androidapplication.utils.loading

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.santanu.android.androidapplication.R

class LoadingDialog(context: Context) : Dialog(context, R.style.LoadingDialog) {
    private val TAG: String = LoadingDialog::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.loading_progress_layout)

    }

}