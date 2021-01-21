package com.santanu.android.application.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.santanu.android.application.R
import com.santanu.android.application.databinding.ActivityMainBinding
import com.santanu.android.application.databinding.FragmentMainBinding
import com.santanu.android.application.ui.base.BaseFragment

class MainFragment private constructor(): BaseFragment<FragmentMainBinding>() {
    private val TAG: String = MainFragment::class.java.simpleName

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    private lateinit var mLayout: FragmentMainBinding

    override val layoutId: Int get() = R.layout.fragment_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mLayout = getViewDataBinding()
        return mLayout.root
    }




}