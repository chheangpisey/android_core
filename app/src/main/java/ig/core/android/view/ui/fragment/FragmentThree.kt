package ig.core.android.view.ui.fragment

import androidx.lifecycle.ViewModelProvider
import ig.core.android.R
import ig.core.android.base.BaseFragment
import ig.core.android.databinding.FragmentThreeBinding
import ig.core.android.di.Injection
import ig.core.android.viewmodel.FragViewModel

class FragmentThree : BaseFragment<FragmentThreeBinding, FragViewModel>() {
    override val mLayoutId = R.layout.fragment_three
    override fun getViewModelClass(): Class<FragViewModel> = FragViewModel::class.java
    override fun getViewModelFactory(): ViewModelProvider.Factory = Injection.provideMainViewModelFactory
    var text: String? = null

    override fun initView() {
        mBinding.fragThree.text = text
    }

    fun setData(str: String) {
        text = str
    }
}