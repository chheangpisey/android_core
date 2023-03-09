package ig.core.android.data.datasource.hilt

import ig.core.android.service.model.RequestUserCreate
import ig.core.android.service.model.ResponseUser
import ig.core.android.service.model.ResponseUserCreated
import ig.core.android.view.ui.activity.hilt.HiltService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HiltRemoteDatasource @Inject constructor(private val hiltService: HiltService) {

    suspend fun gettingUser(): Flow<ResponseUser> = flow {
        emit(hiltService.gettingUser())
    }.flowOn(Dispatchers.IO)

    suspend fun createUser(req: RequestUserCreate): Flow<ResponseUserCreated> = flow {
        emit(hiltService.createUser(req))
    }.flowOn(Dispatchers.IO)

}