package ig.core.android.service.repository

import ig.core.android.service.implement.ApiServiceImpl
import ig.core.android.service.model.RequestLogin
import ig.core.android.service.model.ResponseLogin
import ig.core.android.service.model.custom.StateFlowResponse
import ig.core.android.service.model.custom.StateFlowResponseTest
import ig.core.android.service.model.custom.toStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.jetbrains.anko.custom.asyncResult

class LoginStateFlowRepository(private val apiServiceImpl: ApiServiceImpl) {
    fun loginState(req: RequestLogin): Flow<ResponseLogin> = flow {
        emit(apiServiceImpl.login(req))
    }.flowOn(Dispatchers.IO)

    fun loginStateTest(req: RequestLogin): Flow<StateFlowResponseTest<ResponseLogin>> = flow {
        emit(apiServiceImpl.loginTest(req))
    }.flowOn(Dispatchers.IO)
}
