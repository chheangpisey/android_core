package ig.core.android.view.ui.activity.hilt

import androidx.lifecycle.viewModelScope
import ig.core.android.utils.stateflow.BaseResponse
import ig.core.android.utils.stateflow.handleHttpErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import ig.core.android.base.BaseViewModel
import ig.core.android.service.model.RequestUserCreate
import ig.core.android.service.model.ResponseUser
import ig.core.android.service.model.ResponseUserCreated
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author piseychheang
 * Created on 2/7/23 at 10:40 AM
 * Modified By piseychheang on 2/7/23 at 10:40 AM
 * File name: HiltViewModel.kt
 */

@HiltViewModel
class HiltViewModel @Inject constructor(private val useCase: HiltUseCase) : BaseViewModel() {

    val userName = MutableStateFlow("Pisey Chheang")

    private val _gettingUser = MutableSharedFlow<BaseResponse<ResponseUser>>()
    val gettingUser = _gettingUser

    init {
        viewModelScope.launch {
            useCase.invokeGettingUser()
                .onStart { _gettingUser.emit(BaseResponse.Loading()) }
                .catch { e ->
                    _gettingUser.emit(BaseResponse.Failure(handleHttpErrorResponse(e)))
                }.collectLatest { data ->
                    _gettingUser.emit(BaseResponse.Success(data))
                }
        }
    }

    var requestBody = MutableSharedFlow<RequestUserCreate>(replay = 0)
    private val _createUser = MutableSharedFlow<BaseResponse<ResponseUserCreated>>()
    val createUser = _createUser

    @OptIn(ExperimentalCoroutinesApi::class)
    val fetchingUserCreatedResponse = requestBody.mapLatest {
        useCase.invokeCreateUser(it)
            .onStart { _createUser.emit(BaseResponse.Loading()) }
            .catch { e ->
                _createUser.emit(BaseResponse.Failure(handleHttpErrorResponse(e)))
            }.collectLatest { data ->
                _createUser.emit(BaseResponse.Success(data))
            }
    }
}