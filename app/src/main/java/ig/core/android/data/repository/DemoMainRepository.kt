
package ig.core.android.data.repository

import ig.core.android.data.implement.demomain.DemoMainDataSourceImpl
import ig.core.android.webservice.WebService

class DemoMainRepository(
    private val webService: WebService,
    private val dataLocal: DemoMainDataSourceImpl,
    private val dataRemote: DemoMainDataSourceImpl
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
}