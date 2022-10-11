package ig.core.android.service.model.custom

import androidx.lifecycle.*
import ig.core.android.service.model.ResponseLogin
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*

/**
 * Created by: CHHEANG PISEY
 * Date on: 29-08-2022
 */
sealed class StateFlowResponseTest<T>(val messageTitle: String?) {
//    object Loading : StateFlowResponse()
    class Loading<T> : StateFlowResponseTest<T>("")
    class Failure<T>(val msg: String) : StateFlowResponseTest<T>("")
    class Success<T: Any>(val data: T) : StateFlowResponseTest<T>("")
    //object Empty : StateFlowResponse()
    class Empty<T> : StateFlowResponseTest<T>("")
}

sealed class StateFlowResponse {
    object Loading : StateFlowResponse()
    class Failure(val msg: String) : StateFlowResponse()
    class Success<T>(val data: T) : StateFlowResponse()
    object Empty : StateFlowResponse()
}

//_requestFlowCollector: Flow<*>

/**
 * Request waiting listener for action*/
//fun <T> stateFlowRequestResponse(
//    scope: CoroutineScope,
//    _isActive: MutableSharedFlow<Boolean>,
//    _responseStateFlow: MutableSharedFlow<StateFlowResponse<T>>,
//    onAction: (scope: CoroutineScope) -> Unit
//) {
//    scope.launch {
//        println("_isActive: $_isActive")
//        _isActive.collectLatest {
//            if (it) {
//                println("_isActive: calling")
//                //_responseStateFlow.emit(StateFlowResponse.Loading)
//                onAction.invoke(scope)
//            }
//        }
//    }
//}

/**
 * Request no listener for action*/
//fun <T> stateFlowRequestResponse(
//    _responseStateFlow: MutableSharedFlow<StateFlowResponse<T>>,
//    onAction: () -> Unit
//) {
//    //  _responseStateFlow.value = StateFlowResponse.Loading
//    // _responseStateFlow.emit(StateFlowResponse.Loading)
//    onAction.invoke()
//
//}

/**
 * Is Working to collect value when state onStart
 */
fun <T> Flow<T>.collectWhenCreated(
    scope: CoroutineScope,
    lifecycleOwner: LifecycleOwner,
    action: FlowCollector<T>
) {
    scope.launch {
        lifecycleOwner.whenCreated {
            collect(action)
        }
    }
}

fun <T> Flow<T>.collectWhenStarted(
    scope: CoroutineScope,
    lifecycleOwner: LifecycleOwner,
    action: FlowCollector<T>
) {
    scope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect(action)
        }
    }
}

fun <R> Flow<R>.toStateFlow(coroutineScope: CoroutineScope, initialValue: R) =
    stateIn(coroutineScope, SharingStarted.Lazily, initialValue)
