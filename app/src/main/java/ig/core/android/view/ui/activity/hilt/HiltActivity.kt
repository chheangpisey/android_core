package ig.core.android.view.ui.activity.hilt

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import ig.core.android.utils.stateflow.BaseResponse
import dagger.hilt.android.AndroidEntryPoint
import ig.core.android.R
import ig.core.android.base.BaseNewActivity
import ig.core.android.databinding.ActivityHiltBinding
import ig.core.android.utils.stateflow.collectData
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HiltActivity : BaseNewActivity<ActivityHiltBinding, HiltViewModel>() {

    override val layoutId: Int = R.layout.activity_hilt
    override val viewModel: HiltViewModel by viewModels()

    override fun initView() {
        Log.d("GetDataFromViewModel", viewModel.userName.value)

        //Method GET
        lifecycleScope.launchWhenCreated {
            requestGettingUser()
        }

        //Method POST
        viewModel.createUser.collectData(lifecycleScope, this, Lifecycle.State.STARTED) {
            requestCreateUser()
        }

    }

    private suspend fun requestGettingUser() {
        viewModel.gettingUser.collect {
            handleFlowResponse(it)
            when (it) {

                is BaseResponse.Success -> {
                    Log.d("GettingUser", "Success: ${it.data.data}")
                }

                else -> Log.d("GettingUser", "Empty....")
            }
        }
    }

    private suspend fun requestCreateUser() {
        viewModel.createUser.collect {
            handleFlowResponse(it)
            when (it) {

                is BaseResponse.Success -> {
                    Log.d("CreateUser", "Success: ${it.data}")
                }

                else -> Log.d("CreateUser", "Empty....")
            }
        }
    }
}