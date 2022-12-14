package ig.core.android.view.ui.activity.main

import androidx.lifecycle.*
import ig.core.android.base.BaseViewModel
import ig.core.android.data.repository.MainStateFlowRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by: CHHEANG PISEY
 * Date on: 29-08-2022
 */
class MainViewModel @Inject constructor(private val mainRepository: MainStateFlowRepository) : BaseViewModel() {
    /*** Live Data*/
    private val _liveData = MutableLiveData<String>()
    val liveData: LiveData<String> = _liveData

    /*** State Flow*/
    private val _stateFlow = MutableStateFlow("Data")
    val stateFlow = _stateFlow.asStateFlow()

    /*** Share Flow*/
    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()

    fun usingLiveData() {
        _liveData.value = "Using Live Data"
    }

    fun usingStateFlow() {
        _stateFlow.value = "Using State Flow"
    }

    fun usingFlow(): Flow<Int> {
        return flow {
            repeat(10) {
                emit(it)
                delay(100L)
            }
        }
    }

    fun usingShareFlow() {
        viewModelScope.launch {
            _sharedFlow.emit("Using Shared Flow")
        }
    }

    /**
     * Testing -- StateFlow Method : GET*/
//    private val _postStateFlow: MutableStateFlow<StateFlowResponse> = MutableStateFlow(StateFlowResponse.Empty)
//    val postStateFlow: StateFlow<StateFlowResponse> = _postStateFlow
//    fun getPost() = stateFlowRequestResponse(_postStateFlow) {
//        viewModelScope.launch { mainRepository.getPost()
//            .catch { e ->
//                _postStateFlow.value=StateFlowResponse.Failure(e.message!!)
//            }.collect { data->
//                _postStateFlow.value=StateFlowResponse.Success(data)
//            }
//        }
//    }

}