package ig.core.android.view.ui.activity.navMain

import androidx.lifecycle.ViewModelProvider
import ig.core.android.R
import ig.core.android.base.BaseFragment
import ig.core.android.databinding.FragmentSettingBinding
import ig.core.android.di.Injection
import ig.core.android.viewmodel.FragViewModel

class SettingFragment : BaseFragment<FragmentSettingBinding, FragViewModel>() {
    override val mLayoutId = R.layout.fragment_setting
    override fun getViewModelClass(): Class<FragViewModel> = FragViewModel::class.java
    override fun getViewModelFactory(): ViewModelProvider.Factory = Injection.provideMainViewModelFactory

    override fun initView() {

    }
}