package ig.core.android.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import ig.core.android.R
import ig.core.android.base.BaseViewModel
import ig.core.android.service.model.RequestLogin
import ig.core.android.service.model.ResponseLogin
import ig.core.android.service.model.custom.*
import ig.core.android.service.repository.LoginStateFlowRepository
import ig.core.android.utils.AESUtil
import ig.core.android.utils.AppConstant
import ig.core.android.utils.removeLeadingZero
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Created by: CHHEANG PISEY
 * Date on: 29-08-2022
 */
class LoginViewModel(private val repository: LoginStateFlowRepository) : BaseViewModel() {

    //Login validation message
    private val _validation = MutableSharedFlow<Int>()
    val validation = _validation.asSharedFlow()

    /**
     * We change two way binding with ObservableField to use with: Mutable StateFlow
     */
    val phoneStateFlow = MutableStateFlow("")
    val passwordStateFlow = MutableStateFlow("")

    private var phoneConverted = ""
    private var passwordConverted = ""
    private var requestBody = MutableSharedFlow<RequestLogin>()

    fun requestLogin() {
        if (phoneStateFlow.value.isEmpty()) {
            viewModelScope.launch {
                _validation.emit(R.string.phone_is_require)
            }
            return
        }

        if (passwordStateFlow.value.isEmpty()) {
            viewModelScope.launch {
                _validation.emit(R.string.password_is_require)
            }
            return
        }

        //If phone number have 0 after prefix code and + before remove it out
        phoneConverted = "+855${removeLeadingZero(phoneStateFlow.value)}".replace("+", "")
        Log.d("LOGIN_VM: ", phoneConverted)

        passwordConverted = AESUtil.encrypt(
                AppConstant.ENCRYPTION_KEY,
                AppConstant.VECTOR,
                passwordStateFlow.value)

       val requestLogin = RequestLogin(
            phone = phoneConverted,
            password = passwordConverted,
            loginType = AppConstant.USER_LC_READER
        )

        Log.d("LOGIN_VM: ", "RequestBody: $requestLogin")

        //Emit value to request body after phone/ password and type are filled in.
        viewModelScope.launch {
            requestBody.emit(requestLogin)
        }
    }

    /**
     * Testing -- StateFlow Method : POST
     */
    private val _loginStateFlow: MutableStateFlow<StateFlowResponse> =
        MutableStateFlow(StateFlowResponse.Empty)
    val loginStateFlow: StateFlow<StateFlowResponse> = _loginStateFlow

    @OptIn(ExperimentalCoroutinesApi::class)
    val fetchingLoginResponse = requestBody.mapLatest {
        repository.loginState(it)
            .catch { e ->
                _loginStateFlow.emit(StateFlowResponse.Failure(handleErrorResponse(e)))
            }.collectLatest { data ->
                _loginStateFlow.emit(StateFlowResponse.Success(data))
            }
    }


    //Keep on check more better.....
    private val _login = MutableSharedFlow<StateFlowResponseTest<ResponseLogin>>()
    val login = _login
    @OptIn(ExperimentalCoroutinesApi::class)
    fun eventForTest() = requestBody.mapLatest {
        repository.loginStateTest(it)
            .handleConnectionErrors()
            .collectLatest { data ->
                _login.emit(data)
            }
    }

    private val _demoStateFlow = MutableStateFlow(StateFlowResponse.Empty)
    val demoStateFlow: StateFlow<StateFlowResponse> = _demoStateFlow

}




