package com.santanu.android.androidapplication.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment<T : ViewDataBinding> : DialogFragment() {
    private val TAG: String = BaseDialogFragment::class.java.simpleName

    private var mBaseActivity: BaseActivity<*>? = null
    private var mRootView: View? = null
    private lateinit var mViewDataBinding: T

    @get:LayoutRes
    abstract val layoutId: Int

    open fun getViewDataBinding() : T {
        return mViewDataBinding
    }

    open fun getBaseActivity(): BaseActivity<*>? {
        return mBaseActivity
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*>) {
            mBaseActivity = context
            context.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = mViewDataBinding.root
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()
    }

    override fun onDetach() {
        mBaseActivity?.onFragmentDetached()
        mBaseActivity = null
        super.onDetach()
    }

    

    interface BaseDialogFragmentCallback {
        fun onFragmentAttached()
        fun onFragmentDetached()
    }
}