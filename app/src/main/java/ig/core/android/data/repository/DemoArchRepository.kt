package ig.core.android.data.repository

import ig.core.android.data.datasource.demoarch.DemoArchLocalDatasource
import ig.core.android.data.datasource.demoarch.dblocator.User
import ig.core.android.service.implement.ApiServiceImpl
import ig.core.android.service.model.ResponseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DemoArchRepository(
   // private val webService: WebService,
    private val apiServiceImpl: ApiServiceImpl,
    private val dataLocal: DemoArchLocalDatasource
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

    //Testing Getting User
//    suspend fun requestGettingUser(scope: CoroutineScope): LiveData<ResourceResponse<ResponseUser>> {
//
//        return networkRequest(scope, webService.requestApi.gettingUser()) {
////            scope.launch {
////                fetchDataFromRemoteOrLocal()
////            }
//        }
//    }

    fun gettingUser(): Flow<ResponseUser> = flow {
        emit(apiServiceImpl.gettingUser())
    }.flowOn(Dispatchers.IO)


    suspend fun savingData(data: User) {
        dataLocal.saveUser(data)
    }

    suspend fun gettingData(): User {
       return dataLocal.getUser()
    }

    suspend fun deletingData() {
        return dataLocal.deleteUser()
    }
}