package com.santanu.android.androidapplication.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.santanu.android.androidapplication.R
import com.santanu.android.androidapplication.data.vm.MainViewModel
import com.santanu.android.androidapplication.databinding.ActivityMainBinding
import com.santanu.android.androidapplication.ui.base.BaseActivity
import com.santanu.android.androidapplication.utils.loading.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val TAG: String = MainActivity::class.java.simpleName

    private lateinit var mLayout: ActivityMainBinding
    private val mMainViewModel: MainViewModel by viewModels<MainViewModel>()

    private lateinit var mLoadingDialog: LoadingDialog

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLayout = getViewDataBinding().apply {
            this.lifecycleOwner = this@MainActivity
        }

        /*
        mMainViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(
            application
        ).create(MainViewModel::class.java) */



    }
}