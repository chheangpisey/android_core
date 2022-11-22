package ig.core.android.view.ui.activity.demoArch

import androidx.lifecycle.viewModelScope
import ig.core.android.base.BaseViewModel
import ig.core.android.data.datasource.demoarch.dblocator.User
import ig.core.android.domain.DemoArchUseCase
import ig.core.android.service.model.RequestUserCreate
import ig.core.android.service.model.custom.StateFlowResponse
import ig.core.android.service.model.custom.handleErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DemoArchViewModel(private val demoArchUseCase: DemoArchUseCase) : BaseViewModel() {

    /*** Using LiveData*/
//    val gettingUserResp by lazy {
//        liveData {
//            val data = demoArchUseCase.invoke(viewModelScope)
//            emitSource(data)
//        }
//    }

    /*** Using StateFlow*/
    private val _userStateFlow: MutableStateFlow<StateFlowResponse> =
        MutableStateFlow(StateFlowResponse.Empty)
    val userStateFlow: StateFlow<StateFlowResponse> = _userStateFlow

    val gettingUserResponse = viewModelScope.launch {
        demoArchUseCase.invoke()
            .catch { e ->
                _userStateFlow.emit(StateFlowResponse.Failure(handleErrorResponse(e)))
            }.collectLatest { data ->
                _userStateFlow.emit(StateFlowResponse.Success(data))
            }
    }

    /*** Database stored data*/
    fun setDataToDb(item: User) {
        viewModelScope.launch {
            demoArchUseCase.delete()
            demoArchUseCase.save(item)
        }
    }

    suspend fun getDataFromDb() = withContext(Dispatchers.IO) {
        demoArchUseCase.get()
    }

    suspend fun deleteDataFromDb() = withContext(Dispatchers.IO) {
        demoArchUseCase.delete()
    }

    /*** Create User StateFlow*/
    var requestBody = MutableSharedFlow<RequestUserCreate>()
    private val _createUserStateFlow: MutableStateFlow<StateFlowResponse> =
        MutableStateFlow(StateFlowResponse.Empty)
    val createUserStateFlow: StateFlow<StateFlowResponse> = _createUserStateFlow

    @OptIn(ExperimentalCoroutinesApi::class)
    val fetchingUserCreatedResponse = requestBody.mapLatest {
        demoArchUseCase.invokeCreate(it)
            .catch { e ->
                _createUserStateFlow.emit(StateFlowResponse.Failure(handleErrorResponse(e)))
            }.collectLatest { data ->
                _createUserStateFlow.emit(StateFlowResponse.Success(data))
            }
    }
}