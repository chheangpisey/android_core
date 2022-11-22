package ig.core.android.data.datasource.demoarch

import ig.core.android.data.datasource.demoarch.dblocator.DemoArchDatabase
import ig.core.android.data.datasource.demoarch.dblocator.User
import ig.core.android.data.implement.demoarch.DemoArchDataSourceImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DemoArchLocalDatasource(
    private var database: DemoArchDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DemoArchDataSourceImpl {

    override suspend fun getUser(): User = withContext(dispatcher) {
        database.user().getUser()
    }

    override suspend fun saveUser(user: User) = withContext(dispatcher) {
        database.user().insertUser(user)
    }

    override suspend fun deleteUser() = withContext(dispatcher) {
        database.user().deleteUser()
    }

}
