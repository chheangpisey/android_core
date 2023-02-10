package ig.core.android.view.ui.activity.hilt

import ig.core.android.service.model.ResponseUser
import retrofit2.http.GET

/**
 * @author piseychheang
 * Created on 2/8/23 at 9:34 AM
 * Modified By piseychheang on 2/8/23 at 9:34 AM
 * File name: HiltService.kt
 */

interface HiltService {

    @GET("api/users/2")
    suspend fun gettingUserStateFlow(): ResponseUser
}