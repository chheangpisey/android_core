package ig.core.android.base

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModel

/** 1 - Using StateFlow : Sample GET-METHOD
    private val _getValueStateFlow: MutableStateFlow<StateFlowResponse> =
                                    MutableStateFlow(StateFlowResponse.Empty)
    val getValueStateFlow: StateFlow<StateFlowResponse> = _getValueStateFlow

    val gettingValueResponse = viewModelScope.launch {
        useCase.invoke()
            .catch { e ->
            _getValueStateFlow.emit(StateFlowResponse.Failure(handleErrorResponse(e)))
            }.collectLatest { data ->
            _getValueStateFlow.emit(StateFlowResponse.Success(data))
        }
    }

    2 - Using StateFlow : Sample POST-METHOD
    var requestBody = MutableSharedFlow<REQUEST_BODY>()
    private val _setValueStateFlow: MutableStateFlow<StateFlowResponse> =
                                    MutableStateFlow(StateFlowResponse.Empty)
    val setValueStateFlow: StateFlow<StateFlowResponse> = _setValueStateFlow

    @OptIn(ExperimentalCoroutinesApi::class)
    val fetchingValueResponse = requestBody.mapLatest {
        useCase.invokeCreate(it)
            .catch { e ->
            _setValueStateFlow.emit(StateFlowResponse.Failure(handleErrorResponse(e)))
            }.collectLatest { data ->
            _setValueStateFlow.emit(StateFlowResponse.Success(data))
        }
    }
 */
abstract class BaseViewModel : ViewModel() {
    fun onCloseKeyboard(view: View) {
        val imm = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}