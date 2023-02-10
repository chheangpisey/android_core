
package ig.core.android.data.repository

class DemoMainRepository {
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
}