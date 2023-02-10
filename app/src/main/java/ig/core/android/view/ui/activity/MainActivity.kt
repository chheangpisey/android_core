package ig.core.android.view.ui.activity

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import ig.core.android.BuildConfig
import ig.core.android.R
import ig.core.android.base.BaseActivity
import ig.core.android.databinding.ActivityMainBinding
import ig.core.android.di.Injection
import ig.core.android.viewmodel.MainViewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val mLayoutId = R.layout.activity_main
    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java
    override fun getViewModelFactory(): ViewModelProvider.Factory =
        Injection.provideMainViewModelFactory

    override fun initView() {
        mBinding.vm = mViewModel
        directToFeatureFromApp()
    }

    private fun directToFeatureFromApp() {
        mBinding.featureHome.setOnClickListener {
            val intent = Intent()
            intent.setClassName(BuildConfig.APPLICATION_ID, "com.cps.home.HomeDetailActivity")
            startActivity(intent)
        }
    }
}
