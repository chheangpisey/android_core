package ig.core.android.service.model

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