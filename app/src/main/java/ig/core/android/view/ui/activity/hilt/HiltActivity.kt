package ig.core.android.view.ui.activity.hilt

import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import ig.core.android.utils.stateflow.StateFlowResponse
import dagger.hilt.android.AndroidEntryPoint
import ig.core.android.R
import ig.core.android.base.BaseNewActivity
import ig.core.android.databinding.ActivityHiltBinding
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HiltActivity : BaseNewActivity<ActivityHiltBinding, HiltViewModel>() {

    override val layoutId: Int = R.layout.activity_hilt
    override val viewModel: HiltViewModel by viewModels()

    override fun initView() {
        Log.d("GetDataFromViewModel", viewModel.userName.value)

        /**StateFlow need to use in lifecycleScope*/
        lifecycleScope.launchWhenCreated {
            requestGettingUser()
        }
    }

    private suspend fun requestGettingUser() {
        viewModel.userStateFlow.collectLatest {
            handleStateFlowResponse(it)
            when (it) {

                is StateFlowResponse.Success -> {
                    Log.d("Main", "Success: ${it.data.data}")
                }

                else -> Log.d("Main", "Empty....")
            }
        }
    }

}