package ig.core.android.service.model.custom

sealed class ResourceResponse<out T>(val messageTitle: String?) {
    class Loading<out T> : ResourceResponse<T>("")
    data class Success<out T>(val data: T) : ResourceResponse<T>("")
    data class ApiError<out T>(val message: String) : ResourceResponse<T>(message)
    data class NetworkError<out T>(val message: String) : ResourceResponse<T>(message)
    data class UnknownError<out T>(val message: String) : ResourceResponse<T>(message)
}
