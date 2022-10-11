package ig.core.android.service.repository

import androidx.lifecycle.LiveData
import ig.core.android.service.model.PostResponse
import ig.core.android.service.model.PostsModel
import ig.core.android.service.model.custom.ErrorResponse
import ig.core.android.service.model.custom.NetworkResponse
import ig.core.android.service.model.custom.ResourceResponse
import ig.core.android.utils.livedata.SingleLiveEvent
import ig.core.android.utils.retrofit.NetworkRequest
import ig.core.android.webservice.WebService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainRepository(private val webService: WebService) {
    suspend fun getPosts(scope: CoroutineScope): LiveData<ResourceResponse<ArrayList<PostsModel>>> {
        val request = object : NetworkRequest<ArrayList<PostsModel>>() {
            override suspend fun createCall(): NetworkResponse<ArrayList<PostsModel>, ErrorResponse> {
                return webService.requestApi.getPosts()
            }
        }

        scope.launch { request.fetchFromNetwork() }
        return request.asLiveData()
    }

}
