package ig.core.android.domain

import ig.core.android.data.repository.DemoMainRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DemoMainUseCase(
    private val repository: DemoMainRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

}