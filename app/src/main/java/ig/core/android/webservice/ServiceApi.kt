package ig.core.android.webservice

import ig.core.android.service.model.*
import ig.core.android.service.model.custom.ErrorResponse
import ig.core.android.service.model.custom.NetworkResponse
import ig.core.android.service.model.custom.StateFlowResponse
import ig.core.android.service.model.custom.StateFlowResponseTest
import retrofit2.http.*
import java.util.concurrent.Flow

/****
 *
 * Created by ORN TONY on 11/18/17.
 *
 */

interface ServiceApi {
    @POST("instance-order/auth/login")
    suspend fun loginRequest(@Body loginBody: LoginRequestBody): NetworkResponse<LoginResponse, ErrorResponse>

    @GET("photos")
    suspend fun getPosts(): NetworkResponse<ArrayList<PostsModel>, ErrorResponse>


    @GET("posts")
    suspend fun getPostTest(): ArrayList<PostResponse>

    @POST("laundry/auth/mobile/reader-lc-login")
    suspend fun lcLoginState(
        @Body b: RequestLogin
    ): ResponseLogin

    @POST("laundry/auth/mobile/reader-lc-login")
    suspend fun lcLoginStateTest(
        @Body b: RequestLogin
    ): StateFlowResponseTest<ResponseLogin>

    @POST("laundry/auth/mobile/reader-lc-login")
    suspend fun lcLoginStateFlow(
        @Body b: RequestLogin
    ): StateFlowResponseTest<ResponseLogin>

    @GET("api/users/2")
    suspend fun gettingUser(): NetworkResponse<ResponseUser, ErrorResponse>

    @GET("api/users/2")
    suspend fun gettingUserStateFlow(): ResponseUser

    @POST("api/users")
    suspend fun createUserStateFlow(
        @Body requestUserCreate: RequestUserCreate
    ): ResponseUserCreated
}