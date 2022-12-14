package ig.core.android.domain

import ig.core.android.data.datasource.demoarch.dblocator.User
import ig.core.android.data.repository.DemoArchCreateUserRepository
import ig.core.android.data.repository.DemoArchRepository
import ig.core.android.service.model.RequestUserCreate
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DemoArchUseCase(
    private val repository: DemoArchRepository,
    private val createUserRepository: DemoArchCreateUserRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend fun invoke() = withContext(defaultDispatcher) {
       // repository.requestGettingUser(scope)
        repository.gettingUser()
    }

    suspend fun get() = withContext(defaultDispatcher) {
        repository.gettingData()
    }

    suspend fun save(data: ArrayList<User>) = withContext(defaultDispatcher) {
        repository.savingData(data)
    }

    suspend fun delete() = withContext(defaultDispatcher) {
        repository.deletingData()
    }

    suspend fun invokeCreate(req: RequestUserCreate) = withContext(defaultDispatcher) {
        createUserRepository.creatingUser(req)
    }
}