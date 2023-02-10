package ig.core.android.data.repository

/**
 * @author piseychheang
 * Created on 11/22/22 at 9:22 AM
 * Modified By piseychheang on 11/22/22 at 9:22 AM
 * File name: DemoArchCreateUserRepository.kt
 */
import ig.core.android.service.implement.ApiServiceImpl
import ig.core.android.service.model.RequestUserCreate
import ig.core.android.service.model.ResponseUserCreated
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DemoArchCreateUserRepository(
    private val apiServiceImpl: ApiServiceImpl
) {
    /**
    1- POST - PUT Method:
    suspend fun sampleRequestBody(scope: CoroutineScope, req: [Your Request Model]):
    LiveData<ResourceResponse<[Your Response Model]>> {
    return networkRequest(scope, [Calling to service api])
    }
    2- GET Method:
    suspend fun sampleRequestGet(scope: CoroutineScope):
    LiveData<ResourceResponse<[Your Response Model]>> {
    return networkRequest(scope, [Calling to service api])
    }
     */

    fun creatingUser(requestUserCreate: RequestUserCreate): Flow<ResponseUserCreated> = flow {
        emit(apiServiceImpl.createUser(requestUserCreate))
    }.flowOn(Dispatchers.IO)
}
