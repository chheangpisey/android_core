package ig.core.android.view.ui.activity.demoArch

import androidx.lifecycle.viewModelScope
import ig.core.android.base.BaseViewModel
import ig.core.android.data.datasource.demoarch.dblocator.User
import ig.core.android.domain.DemoArchUseCase
import ig.core.android.service.model.RequestUserCreate
import ig.core.android.service.model.ResponseUser
import ig.core.android.service.model.ResponseUserCreated
import ig.core.android.service.model.custom.StateFlowResponse
import ig.core.android.service.model.custom.handleHttpErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
class DemoArchViewModel(private val demoArchUseCase: DemoArchUseCase) : BaseViewModel() {

    /*** Using LiveData*/
//    val gettingUserResp by lazy {
//        liveData {
//            val data = demoArchUseCase.invoke(viewModelScope)
//            emitSource(data)
//        }
//    }

    /*** Get StateFlow:  Method GET*/
    private val _userStateFlow: MutableStateFlow<StateFlowResponse<ResponseUser>> =
        MutableStateFlow(StateFlowResponse.Loading())
    val userStateFlow: StateFlow<StateFlowResponse<ResponseUser>> = _userStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            _userStateFlow.emit(StateFlowResponse.Loading())
            demoArchUseCase.invoke()

                .catch { e ->
                    _userStateFlow.emit(StateFlowResponse.Failure(handleHttpErrorResponse(e)))
                }.collectLatest { data ->
                    _userStateFlow.emit(StateFlowResponse.Success(data))
                }
        }
    }

    /*** Database stored data*/
    fun setDataToDb(item: ArrayList<User>) {
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

    /*** Create User StateFlow: Method POST*/
    var requestBody = MutableSharedFlow<RequestUserCreate>()
    private val _createUserStateFlow: MutableStateFlow<StateFlowResponse<ResponseUserCreated>> =
        MutableStateFlow(StateFlowResponse.Loading())
    val createUserStateFlow: StateFlow<StateFlowResponse<ResponseUserCreated>> =
        _createUserStateFlow.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val fetchingUserCreatedResponse = requestBody.mapLatest {
        _createUserStateFlow.emit(StateFlowResponse.Loading())
        demoArchUseCase.invokeCreate(it)
            .catch { e ->
                _createUserStateFlow.emit(StateFlowResponse.Failure(handleHttpErrorResponse(e)))
            }.collectLatest { data ->
                _createUserStateFlow.emit(StateFlowResponse.Success(data))
            }
    }

//    init {
//        requestBody.mapLatest {
//            _createUserStateFlow.emit(StateFlowResponse.Loading())
//            demoArchUseCase.invokeCreate(it)
//                .catch { e ->
//                    _createUserStateFlow.emit(StateFlowResponse.Failure(handleHttpErrorResponse(e)))
//                }.collectLatest { data ->
//                    _createUserStateFlow.emit(StateFlowResponse.Success(data))
//                }
//        }
//    }
}