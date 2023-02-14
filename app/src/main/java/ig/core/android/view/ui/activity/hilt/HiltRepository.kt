package ig.core.android.view.ui.activity.hilt

import ig.core.android.data.datasource.hilt.HiltRemoteDatasource
import ig.core.android.service.model.ResponseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

/**
 * @author piseychheang
 * Created on 2/7/23 at 10:38 AM
 * Modified By piseychheang on 2/7/23 at 10:38 AM
 * File name: HiltRepository.kt
 */

//class HiltRepository @Inject constructor(
//    private val hiltService: HiltService
//) {
//    fun gettingUser(): Flow<ResponseUser> = flow {
//        emit(hiltService.gettingUserStateFlow())
//    }.flowOn(Dispatchers.IO)
//
//}

class HiltRepository @Inject constructor(
    private val remote: HiltRemoteDatasource
) {

    suspend fun gettingUser() = remote.gettingUser()
}