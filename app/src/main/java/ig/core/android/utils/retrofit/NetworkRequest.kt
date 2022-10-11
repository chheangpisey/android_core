package ig.core.android.utils.retrofit

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ig.core.android.service.model.custom.ErrorResponse
import ig.core.android.service.model.custom.NetworkResponse
import ig.core.android.service.model.custom.ResourceResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class NetworkRequest<T> @MainThread constructor() {
    private val result = MutableLiveData<ResourceResponse<T>>()

    @MainThread
    private fun setValue(newValue: ResourceResponse<T>) {
        if (result.value != newValue) {
            result.postValue(newValue)
        }
    }

    suspend fun fetchFromNetwork() {
        setValue(ResourceResponse.Loading())
        withContext(Dispatchers.IO) {
            when (val response = createCall()) {
                is NetworkResponse.Success -> {
                    setValue(ResourceResponse.Success(response.body))
                }
                is NetworkResponse.ApiError -> {
                    setValue(ResourceResponse.ApiError(response.body.message))
                }
                is NetworkResponse.NetworkError -> {
                    setValue(ResourceResponse.NetworkError(response.error.message.toString()))
                }
                is NetworkResponse.UnknownError -> {
                    setValue(ResourceResponse.UnknownError(response.error?.message.toString()))
                }
            }
        }
    }

    fun asLiveData() = result as LiveData<ResourceResponse<T>>

    @MainThread
    protected abstract suspend fun createCall(): NetworkResponse<T, ErrorResponse>
}

/**
 * Modified by: CHHEANG PISEY
 * Date on: 29-08-2022
 */
fun <T> networkRequest(scope: CoroutineScope,
                       resp: NetworkResponse<T, ErrorResponse>): LiveData<ResourceResponse<T>> {
    val request =  object: NetworkRequest<T>() {
        override suspend fun createCall(): NetworkResponse<T, ErrorResponse> {
            return resp
        }
    }
    scope.launch { request.fetchFromNetwork() }
    return request.asLiveData()
}