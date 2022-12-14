package com.cps.home

import androidx.lifecycle.ViewModelProvider
import com.cps.home.databinding.FragmentHomeDetailBinding
import ig.core.android.base.BaseActivity
import ig.core.android.di.Injection

class HomeDetailActivity: BaseActivity<FragmentHomeDetailBinding, FragViewModel>() {
    override val mLayoutId = R.layout.fragment_home_detail
    override fun getViewModelClass(): Class<FragViewModel> = FragViewModel::class.java
    override fun getViewModelFactory(): ViewModelProvider.Factory = Injection.provideDemoArchViewModelFactory(this)

    override fun initView() {

    }
}