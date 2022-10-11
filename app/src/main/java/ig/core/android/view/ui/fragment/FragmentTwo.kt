package ig.core.android.view.ui.fragment

import android.content.Context
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ig.core.android.R
import ig.core.android.base.BaseFragment
import ig.core.android.databinding.FragmentTwoBinding
import ig.core.android.di.Injection
import ig.core.android.viewmodel.FragViewModel

class FragmentTwo : BaseFragment<FragmentTwoBinding, FragViewModel>() {
    override val mLayoutId = R.layout.fragment_two
    override fun getViewModelClass(): Class<FragViewModel> = FragViewModel::class.java
    override fun getViewModelFactory(): ViewModelProvider.Factory = Injection.provideMainViewModelFactory

    private var mListener: OnFragmentCommunicationListener? = null

    override fun initView() {
        viewModel.data.observe(this, Observer { mBinding.tvFragTwo.text = it })
        mBinding.btnFragTwo.setOnClickListener {
            mListener!!.setData("Hello Fragment Three")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = if (context is OnFragmentCommunicationListener) {
            context
        } else {
            throw RuntimeException("$context must implement OnFragmentCommunicationListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentCommunicationListener {
        fun setData(str: String)
    }
}