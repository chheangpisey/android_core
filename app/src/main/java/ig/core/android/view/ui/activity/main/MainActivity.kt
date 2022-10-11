package ig.core.android.view.ui.activity.main

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import ig.core.android.R
import ig.core.android.base.BaseActivity
import ig.core.android.databinding.ActivityMainBinding
import ig.core.android.di.Injection
import ig.core.android.service.model.custom.StateFlowResponse
import ig.core.android.viewmodel.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val mLayoutId = R.layout.activity_main
    override fun getViewModelClass(): Class<MainViewModel> = MainViewModel::class.java
    override fun getViewModelFactory(): ViewModelProvider.Factory = Injection.provideMainViewModelFactory

    override fun initView() {
        onSetUpGeneralToolbar(getString(R.string.main_activity))
        stateFlowTest()

        mBinding.txtLiveData.setOnClickListener {
            mViewModel.usingLiveData()
        }

        mBinding.txtStateFlow.setOnClickListener {
            mViewModel.usingStateFlow()
        }

        mBinding.txtFlow.setOnClickListener {
            lifecycleScope.launch {
                mViewModel.usingFlow().collectLatest {
                    mBinding.txtFlow.text = "$it"
                }
            }
        }

        mBinding.txtSharedFlow.setOnClickListener {

        }

        subscribeToObservables()
    }

    private fun subscribeToObservables() {
        mViewModel.liveData.observe(this) {
            mBinding.txtLiveData.text = it
        }

        lifecycleScope.launchWhenCreated {
            mViewModel.stateFlow.collectLatest {
                mBinding.txtStateFlow.text = it
            }
        }
    }

    private fun stateFlowTest() {
        gettingPostStateFlow()
    }

    private fun gettingPostStateFlow() {
//        mViewModel.getPost()
//        lifecycleScope.launchWhenStarted {
//            mViewModel.postStateFlow.collect {
////                when(it){
////                    is StateFlowResponse.Loading-> {
////                        Log.d("Main", "Loading....")
////                    }
////
////                    is StateFlowResponse.Failure -> {
////                        Log.d("Main", "onCreate: ${it.msg}")
////                    }
////
////                    is StateFlowResponse.Success<*> -> {
////                        Log.d("Main", "Success: ${it.data}")
////                    }
////                    is StateFlowResponse.Empty-> {
////                        Log.d("Main", "Empty....")
////                    }
////                }
//            }
//        }
    }

}
