package com.cps.featurefavorite

import androidx.lifecycle.ViewModelProvider
import com.cps.featurefavorite.databinding.FragmentFavoriteBinding
import ig.core.android.base.BaseFragment
import ig.core.android.di.Injection

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FragViewModel>() {
    override val mLayoutId = R.layout.fragment_favorite
    override fun getViewModelClass(): Class<FragViewModel> = FragViewModel::class.java
    override fun getViewModelFactory(): ViewModelProvider.Factory = Injection.provideMainViewModelFactory

    override fun initView() {

    }
}