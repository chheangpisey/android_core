package ig.core.android.service.repository

import ig.core.android.service.implement.ApiServiceImpl
import ig.core.android.service.model.RequestLogin
import ig.core.android.service.model.ResponseLogin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginStateFlowRepository(private val apiServiceImpl: ApiServiceImpl) {
    fun loginState(req: RequestLogin): Flow<ResponseLogin> = flow {
        emit(apiServiceImpl.login(req))
    }.flowOn(Dispatchers.IO)

}
