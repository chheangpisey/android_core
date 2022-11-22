package ig.core.android.view.ui.activity.demoArch

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import ig.core.android.R
import ig.core.android.base.BaseActivity
import ig.core.android.databinding.ActivityDemoArchBinding
import ig.core.android.di.Injection
import ig.core.android.service.model.RequestUserCreate
import ig.core.android.service.model.ResponseUser
import ig.core.android.service.model.ResponseUserCreated
import ig.core.android.service.model.custom.StateFlowResponse
import ig.core.android.service.model.custom.collectWhenCreated
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DemoArchActivity : BaseActivity<ActivityDemoArchBinding, DemoArchViewModel>() {

    override val mLayoutId = R.layout.activity_demo_arch
    override fun getViewModelClass(): Class<DemoArchViewModel> = DemoArchViewModel::class.java
    override fun getViewModelFactory(): ViewModelProvider.Factory =
        Injection.provideDemoArchViewModelFactory(this)

    override fun initView() {
        /** Coding here...*/

        //requestUser()

        //invokeOnCompletion: start this job immediately
        mViewModel.gettingUserResponse.invokeOnCompletion {
            lifecycleScope.launchWhenCreated {
                requestGetUserStateFlow()
            }
        }

        mViewModel.fetchingUserCreatedResponse.collectWhenCreated(lifecycleScope, this) {
            requestUserCreatedStateFlow()
        }

        lifecycleScope.launch {
            Log.d("GettingDataInActivity: ", "${mViewModel.getDataFromDb()}")

            val request = RequestUserCreate(
                name = "Pisey",
                job = "IT"
            )
            mViewModel.requestBody.emit(request)
        }

    }

    private suspend fun requestGetUserStateFlow() {
        mViewModel.userStateFlow.collectLatest {
            handleStateFlowResponse(it)
            when (it) {
                is StateFlowResponse.Loading -> {
                    Log.d("Main", "Loading....")
                    showLoading(0.3F)
                }

                is StateFlowResponse.Failure -> Log.d("Main", "onCreate: ${it.msg}")

                is StateFlowResponse.Success<*> -> {
                    Log.d("Main", "Success: ${it.data}")
                    val result = it.data as ResponseUser
                    mViewModel.setDataToDb(result.data)
                }

                is StateFlowResponse.Empty -> Log.d("Main", "Empty....")
            }
        }
    }

    private suspend fun requestUserCreatedStateFlow() {
        mViewModel.createUserStateFlow.collectLatest {
            handleStateFlowResponse(it)
            when (it) {
                is StateFlowResponse.Loading -> {
                    Log.d("Main", "Loading....")
                    showLoading(0.3F)
                }

                is StateFlowResponse.Failure -> Log.d("Main", "onCreate: ${it.msg}")

                is StateFlowResponse.Success<*> -> {
                    Log.d("Main", "Success: ${it.data}")
                    val result = it.data as ResponseUserCreated
                    Log.d("CreatedUserResponse", "$result")
                }

                is StateFlowResponse.Empty -> Log.d("Main", "Empty....")
            }
        }
    }

//  private fun requestUser() {
//    mViewModel.gettingUserResp.observe(this) {
//      handleResponseRequest(it)
//      when(it) {
//        is ResourceResponse.Success -> {
//          Log.d("SuccessResponseUser: ", "${it.data}")
//          mViewModel.setDataToDb(it.data.data)
//
//          lifecycleScope.launch {
//            val data = mViewModel.getDataFromDb()
//            println("GettingDataInActivity: $data")
//          }
//
//        }
//        else -> {}
//      }
//    }
//  }
}