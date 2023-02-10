package ig.core.android.service.repository

import ig.core.android.service.implement.ApiServiceImpl
import ig.core.android.service.model.PostResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainStateFlowRepository @Inject constructor(private val apiServiceImpl: ApiServiceImpl) {
    fun getPost(): Flow<ArrayList<PostResponse>> = flow {
        emit(apiServiceImpl.getPostTest())
    }.flowOn(Dispatchers.IO)
}