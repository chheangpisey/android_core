package ig.core.android.view.ui.activity.demoArch

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import ig.core.android.R
import ig.core.android.base.BaseActivity
import ig.core.android.data.datasource.demoarch.dblocator.User
import ig.core.android.databinding.ActivityDemoArchBinding
import ig.core.android.di.Injection
import ig.core.android.service.model.RequestUserCreate
import ig.core.android.service.model.custom.StateFlowResponse
import ig.core.android.service.model.custom.collectWhenCreated
import ig.core.android.service.model.custom.collectWhenStarted
import ig.core.android.view.adapter.DemoAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DemoArchActivity : BaseActivity<ActivityDemoArchBinding, DemoArchViewModel>() {

    override val mLayoutId = R.layout.activity_demo_arch
    override fun getViewModelClass(): Class<DemoArchViewModel> = DemoArchViewModel::class.java
    override fun getViewModelFactory(): ViewModelProvider.Factory =
        Injection.provideDemoArchViewModelFactory(this)

    override fun initView() {
        /** Coding here...*/
        mBinding.demo = mViewModel

        lifecycleScope.launch {
            /**For getting data*/
            Log.d("GettingDataInActivity: ", "${mViewModel.getDataFromDb()}")
            if (mViewModel.getDataFromDb().isNotEmpty())
                mBinding.rvDemo.adapter = DemoAdapter(mViewModel.getDataFromDb() as ArrayList<User>) {}

            /**For create data*/
            val request = RequestUserCreate(
                name = "Pisey",
                job = "IT"
            )
            mViewModel.requestBody.emit(request)
        }

        //GET METHOD
        mViewModel.userStateFlow.collectWhenStarted(lifecycleScope, this) {
            requestGetUserStateFlow()
        }

        //POST METHOD
        mViewModel.fetchingUserCreatedResponse.collectWhenCreated(lifecycleScope, this) {
            requestUserCreatedStateFlow()
        }
//
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED) {
//                requestUserCreatedStateFlow()
//            }
//        }
    }

    private suspend fun requestGetUserStateFlow() {
        mViewModel.userStateFlow.collectLatest {
            handleStateFlowResponse(it)
            when (it) {
                is StateFlowResponse.Loading -> {
                    Log.d("DemoGet", "Loading....")
                    showLoading(0.3F)
                }

                is StateFlowResponse.Failure -> Log.d("DemoGet", "onCreate: ${it.msg}")

                is StateFlowResponse.Success -> {
                    Log.d("DemoGet", "Success: ${it.data}")
                    mViewModel.setDataToDb(it.data.data)


                    /**Way 1 for init data into RV*/
//                    mBinding.rvDemo.also { rv->
//                        rv.adapter = DemoAdapter(result.data) {
//
//                        }
//                    }

                    /**Way 2 for init data into RV*/
                    mBinding.rvDemo.adapter = DemoAdapter(it.data.data) {}
                }

                is StateFlowResponse.Empty -> Log.d("DemoGet", "Empty....")
            }
        }
    }

    private suspend fun requestUserCreatedStateFlow() {
        mViewModel.createUserStateFlow.collectLatest {
            handleStateFlowResponse(it)
            when (it) {
                is StateFlowResponse.Loading -> {
                    Log.d("DemoUserCreated", "Loading....")
                    showLoading(0.3F)
                }

                is StateFlowResponse.Failure -> Log.d("DemoUserCreated", "onCreate: ${it.msg}")

                is StateFlowResponse.Success -> {
                    Log.d("DemoUserCreated", "${it.data}")
                }

                is StateFlowResponse.Empty -> Log.d("DemoUserCreated", "Empty....")
            }
        }
    }
}