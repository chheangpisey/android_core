package ig.core.android.data.datasource.hilt

import ig.core.android.service.model.ResponseUser
import ig.core.android.view.ui.activity.hilt.HiltService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HiltRemoteDatasource @Inject constructor(private val hiltService: HiltService) {

    suspend fun gettingUser(): Flow<ResponseUser> = flow {
        emit(hiltService.gettingUserStateFlow())
    }.flowOn(Dispatchers.IO)

}