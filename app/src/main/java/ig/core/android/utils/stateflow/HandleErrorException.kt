package ig.core.android.utils.stateflow

import android.content.Context
import android.util.Log
import ig.core.android.utils.onAlertMessage
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.sql.Timestamp

/**
 * Check When Request to server and getting response back to show alert dialog
 * @param context: Used  to get access to resources in fragment or activity
 * @param codeServer: Code message from server response
 * @param messageServer: String message from server response
 * @param funName: Title for alert dialog
 * @param onAction: Any action you want to handle
 */
fun handleErrorResponse(
    context: Context, codeServer: Int, funName: String, messageServer: String,
    onAction: () -> Unit
) {
    if (codeServer == 200) onAction.invoke()
    if (codeServer != 200 && codeServer != 406)
        onAlertMessage(context, funName, messageServer)
}

/**Handle Error in HttpException and SocketTimeoutException*/
fun handleHttpErrorResponse(e: Throwable): String {
    return when (e) {
        is HttpException -> parseHTTPErrorResponse(e.response()!!)

        is SocketTimeoutException ->  e.message!!

        else -> e.message!!
    }
}

/**Developer can custom message response for easy understand based code error from httpException*/
fun parseHTTPErrorResponse(responseBody: Response<*>): String {
    val messageError = when(responseBody.code()) {
        400 -> "Bad Request"
        401 -> "UnAuthorized"
        404 -> "Error Not Found: Incorrect URL or Endpoint"
        405 -> "Method Request Not Allowed"
        500 -> "Internal Server Error: The request was rejected cause of endpoint is wrong format."
        502 -> "Bad Gateway"
        else -> ""
    }

    val errorBody = hashMapOf(
        "timeStamp" to Timestamp(System.currentTimeMillis()),
        "code" to responseBody.code(),
        "message" to messageError,
        "path" to responseBody.raw().request().url()
    )

    Log.d("ParseHTTP", "errorBody: $errorBody")

    return messageError
}