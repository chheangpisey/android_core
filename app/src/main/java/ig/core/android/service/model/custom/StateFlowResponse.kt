package ig.core.android.service.model.custom

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.whenCreated
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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
