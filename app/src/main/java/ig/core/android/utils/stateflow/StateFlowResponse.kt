package ig.core.android.utils.stateflow

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * Created by: CHHEANG PISEY
 * Date on: 29-08-2022
 */

sealed class StateFlowResponse<out T>(val messageTitle: String?) {
    class Loading<out T> : StateFlowResponse<T>("")
    class Failure<out T>(val msg: String) : StateFlowResponse<T>(msg)
    class Success<T>(val data: T) : StateFlowResponse<T>("")
    class Empty<out T> : StateFlowResponse<T>("")
}

/**Is Working to collect value when this state is reached in two cases:
 * -start onCreate call
 * -end onResume call.
 * */
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

/**Is Working to collect value when this state is reached in two cases:
 * -start onStart call
 * -end onPause call.
 * */
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

/**Is Working to collect value when this state is reached in onResume call.*/
fun <T> Flow<T>.collectWhenResumed(
    scope: CoroutineScope,
    lifecycleOwner: LifecycleOwner,
    action: FlowCollector<T>
) {
    scope.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            collect(action)
        }
    }
}

fun <R> Flow<R>.toStateFlow(coroutineScope: CoroutineScope, initialValue: R) =
    stateIn(coroutineScope, SharingStarted.Lazily, initialValue)
