package ig.core.android.view.ui.fragment

import androidx.lifecycle.ViewModelProvider
import ig.core.android.R
import ig.core.android.base.BaseFragment
import ig.core.android.databinding.FragmentOneBinding
import ig.core.android.di.Injection
import ig.core.android.view.ui.activity.main.MainFragment
import ig.core.android.viewmodel.FragViewModel

class FragmentOne : BaseFragment<FragmentOneBinding, FragViewModel>() {
    override val mLayoutId = R.layout.fragment_one
    override fun getViewModelClass(): Class<FragViewModel> = FragViewModel::class.java
    override fun getViewModelFactory(): ViewModelProvider.Factory = Injection.provideMainViewModelFactory

    override fun initView() {
        mBinding.btnFragOne.setOnClickListener {
            viewModel.setData("Hello Fragment Two")
            (context as MainFragment).onReplaceFragmentStack(FragmentTwo())
        }
    }
}