package ig.core.android.view.ui.activity.login

import android.content.Intent
import android.util.Log
import androidx.lifecycle.*
import com.cps.register.RegisterActivity
import ig.core.android.R
import ig.core.android.base.BaseActivity
import ig.core.android.databinding.ActivityLoginBinding
import ig.core.android.di.Injection
import ig.core.android.service.model.ResponseLogin
import ig.core.android.service.model.custom.*
import ig.core.android.utils.snackBar
import ig.core.android.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.anko.contentView
import org.jetbrains.anko.startActivity

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override val mLayoutId = R.layout.activity_login
    override fun getViewModelClass(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun getViewModelFactory(): ViewModelProvider.Factory =
        Injection.provideLoginViewModelFactory

    override fun initView() {
        mBinding.vm = mViewModel
        mViewModel.validation.collectWhenStarted(lifecycleScope, this) {
            snackBar(contentView!!.rootView, getString(it))
        }

        mViewModel.fetchingLoginResponse.collectWhenCreated(lifecycleScope, this) {
            requestLoginStateFlow()
        }
//        mViewModel.eventForTest().collectWhenCreated(lifecycleScope, this) {
//            eventTest()
//        }

        testModule()
    }

    private suspend fun requestLoginStateFlow() {
            mViewModel.loginStateFlow.collectLatest {
                handleStateFlowResponse(it)
                when (it) {
                    is StateFlowResponse.Loading -> {
                        Log.d("Main", "Loading....")
                        showLoading(0.3F)
                    }

                    is StateFlowResponse.Failure -> {
                        Log.d("Main", "onCreate: ${it.msg}")
                    }

                    is StateFlowResponse.Success<*> -> {
                        Log.d("Main", "Success: ${it.data}")
                        val result = it.data as ResponseLogin
                        handleErrorResponse(result.response.code!!) { msg ->
                            // snackBar(contentView!!.rootView, msg)
                            onAlertError(msg)
                        }
                    }

                    is StateFlowResponse.Empty -> {
                        Log.d("Main", "Empty....")
                    }
                }
            }
    }

    private suspend fun eventTest() {
        mViewModel.login.collect {
           // handleStateFlowResponse(it)
            when (it) {
                is StateFlowResponseTest.Loading -> {
                    Log.d("Main", "Loading....")
                    showLoading(0.3F)
                }

                is StateFlowResponseTest.Failure -> {
                    Log.d("Main", "onCreate: ${it.msg}")
                }

                is StateFlowResponseTest.Success<ResponseLogin> -> {
                    Log.d("Main", "Success: ${it.data}")
                    handleErrorResponse(it.data.response.code!!) { msg ->
                        // snackBar(contentView!!.rootView, msg)
                        onAlertError(msg)
                    }
                }

                is StateFlowResponseTest.Empty -> {
                    Log.d("Main", "Empty....")
                }
            }
        }
    }

    private fun testModule() {
        mBinding.moduleRegister.setOnClickListener {
//            val intent = Intent(this, RegisterActivity::class.java)
//            startActivity(intent)

            val intent = Intent()
            intent.setClassName("ig.core.android", "com.cps.featurehome.HomeActivity")
            startActivity(intent)
        }
    }
}
