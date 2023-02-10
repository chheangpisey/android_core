package ig.core.android.view.ui.activity.hilt

import androidx.lifecycle.viewModelScope
import ig.core.android.utils.stateflow.StateFlowResponse
import ig.core.android.utils.stateflow.handleHttpErrorResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import ig.core.android.base.BaseViewModel
import ig.core.android.service.model.ResponseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
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

    /*** Using StateFlow*/
    private val _userStateFlow: MutableStateFlow<StateFlowResponse<ResponseUser>> =
        MutableStateFlow(StateFlowResponse.Empty())
    val userStateFlow: StateFlow<StateFlowResponse<ResponseUser>> = _userStateFlow

    init {
        viewModelScope.launch {
           // _userStateFlow.emit(StateFlowResponse.Loading())
            useCase.invokeGettingUser()
                .catch { e ->
                    _userStateFlow.emit(StateFlowResponse.Failure(handleHttpErrorResponse(e)))
                }.collectLatest { data ->
                    _userStateFlow.emit(StateFlowResponse.Success(data))
                }
        }
    }
}