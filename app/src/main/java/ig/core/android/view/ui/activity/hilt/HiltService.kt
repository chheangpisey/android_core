package ig.core.android.view.ui.activity.hilt

import ig.core.android.service.model.RequestUserCreate
import ig.core.android.service.model.ResponseUser
import ig.core.android.service.model.ResponseUserCreated
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @author piseychheang
 * Created on 2/8/23 at 9:34 AM
 * Modified By piseychheang on 2/8/23 at 9:34 AM
 * File name: HiltService.kt
 */

interface HiltService {

    @GET("api/users/2")
    suspend fun gettingUser(): ResponseUser

    @POST("api/users")
    suspend fun createUser(
        @Body requestUserCreate: RequestUserCreate
    ): ResponseUserCreated
}