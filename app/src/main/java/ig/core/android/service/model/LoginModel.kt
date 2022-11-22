package ig.core.android.service.model

import ig.core.android.data.datasource.demoarch.dblocator.User

data class LoginRequestBody(var email: String? = "", var password: String? = "", var imei: String? = "")

data class LoginResponse (
        val data: DataLogin? = null,
        val success: Boolean? = true,
        val response: ResponseLoginT? = null,
        val message: String? = null
)

data class DataLogin (
        val accessToken: String? = null,
        val tokenType: String? = null
)

data class ResponseLoginT (
        val code: Int? = 0,
        val message: String? = null
)

data class RequestLogin(
        var phone: String? = "",
        var password: String? = "",
        var loginType: Int? = 0
)


data class ResponseLogin(
        val data: TokenResult,
        val response: ResultLogin)

data class TokenResult(
        val expireIn: Long? = 0,
        val accessToken: String?,
        val tokenType: String?,
        val lcType: ProjectType?)

data class ProjectType(
        val id: Int? = 0,
        val type: String?
)

data class ResultLogin(
        val code: Int? = 0,
        val message: String? = "")

data class ResponseUser(
        val data: User
)

data class RequestUserCreate(
        val name: String,
        val job: String
)

data class ResponseUserCreated(
        val name: String,
        val job: String,
        val id: String,
        val createdAt: String
)

//data class User(
//        val id: Int,
//        val email: String
//)