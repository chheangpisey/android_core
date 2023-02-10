package ig.core.android.view.ui.activity.hilt

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @author piseychheang
 * Created on 2/7/23 at 10:37 AM
 * Modified By piseychheang on 2/7/23 at 10:37 AM
 * File name: HiltUseCase.kt
 */

class HiltUseCase @Inject constructor (
    private val repository: HiltRepository
    ) {

    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default

    suspend fun invokeGettingUser() = withContext(defaultDispatcher) {
        repository.gettingUser()
    }
}