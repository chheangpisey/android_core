package ig.core.android.utils.stateflow

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * Created by: CHHEANG PISEY
 * Date on: 29-08-2022
 */

sealed class BaseResponse<out T>(val messageTitle: String?) {
    class Loading<out T> : BaseResponse<T>("")
    class Failure<T>(msg: String = "") : BaseResponse<T>(msg)
    class Success<T>(val data: T) : BaseResponse<T>("")
    class Empty<out T> : BaseResponse<T>("")
}

/**
 * ->>>>>State: CREATED Is Working to collect value when this state is reached in two cases:
     * -start onCreate call
     * -end onResume call.
 * ->>>>>State: STARTED Is Working to collect value when this state is reached in two cases:
     * -start onStart call
     * -end onPause call.
 * ->>>>>State: STARTED Is Working to collect value when this state is reached in two cases:
     * -start onStart call
     * -end onPause call.
 * ->>>>>State: RESUMED Is Working to collect value when this state is reached in onResume call.
 * */

fun <T> Flow<T>.collectData(
    scope: CoroutineScope,
    lifecycleOwner: LifecycleOwner,
    state: Lifecycle.State,
    action: FlowCollector<T>
) {
    scope.launch {
        lifecycleOwner.repeatOnLifecycle(state) {
            collect(action)
        }
    }
}

fun <R> Flow<R>.toStateFlow(coroutineScope: CoroutineScope, initialValue: R) =
    stateIn(coroutineScope, SharingStarted.Lazily, initialValue)
