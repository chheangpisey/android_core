package ig.core.android.service.model.custom

data class ErrorResponse(val status: String,
                         val message: String)


data class FailureResponse(val timestamp: Long,
                           val status: String,
                           val message: String,
                           val error: String,
                           val path: String)