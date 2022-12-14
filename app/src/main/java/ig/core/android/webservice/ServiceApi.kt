package ig.core.android.webservice

import ig.core.android.service.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ServiceApi {

    @POST("laundry/auth/mobile/reader-lc-login")
    suspend fun lcLoginState(
        @Body b: RequestLogin
    ): ResponseLogin

    @GET("api/users?page=2")
    suspend fun gettingUserStateFlow(): ResponseUser

    @POST("api/users")
    suspend fun createUserStateFlow(
        @Body requestUserCreate: RequestUserCreate
    ): ResponseUserCreated
}