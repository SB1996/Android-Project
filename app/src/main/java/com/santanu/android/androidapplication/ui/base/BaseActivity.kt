package com.santanu.android.androidapplication.ui.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    BottomNavigationView.OnNavigationItemSelectedListener,
    BaseFragment.BaseFragmentCallback,
    BaseDialogFragment.BaseDialogFragmentCallback {
    private val TAG: String = BaseActivity::class.java.simpleName

    private var mViewDataBinding: T? = null

    @LayoutRes
    abstract fun getLayoutId() : Int

    fun getViewDataBinding() : T {
        return mViewDataBinding!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyDataBinding()
    }

    private fun applyDataBinding() {

        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewDataBinding!!.executePendingBindings()
    }
    
    internal fun printLog(massage: String, title: String? = null) {
        if (title.isNullOrEmpty()) {
            Log.d(
                TAG, "BaseActivity{} : printLog() >>" +
                        " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: $massage"
            )
        } else {
            Log.d(
                TAG, "BaseActivity{} : printLog() >>" +
                        " [line ${Thread.currentThread().stackTrace[2].lineNumber}] :: $title : $massage"
            )
        }

    }

    internal fun showToast(context: Context, massage: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, massage, duration).show()
    }

    open fun showSnackBar(view: View?, massage: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(view!!, massage, duration).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }

    /*override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionList.permissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                
            } else {
                
                ApplicationPermission().requestPermissions(
                    this, PermissionList.permissionList, PermissionList.permissionCode
                )
            }
        }
    }*/
    
    override fun onBackPressed() {
        super.onBackPressed()
    }


    
    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached() {

    }
}