package ig.core.android.data.implement.demoarch

import ig.core.android.data.datasource.demoarch.dblocator.User

/**
 * @author piseychheang
 * Created on 11/2/22 at 8:37 AM
 * Modified By piseychheang on 11/2/22 at 8:37 AM
 * File name: DemoArchDataSourceImpl.kt
 */

interface DemoArchDataSourceImpl {

    suspend fun saveUser(user: User)

    suspend fun deleteUser()

    suspend fun getUser(): User

}