package ig.core.android.service.model.custom

import android.content.Context
import android.util.Log
import ig.core.android.utils.onAlertMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.sql.Timestamp

/**
 * Created by: CHHEANG PISEY
 * Date on: 29-08-2022
 */
fun <T> Flow<T>.handleHTTPErrors(): Flow<T> = flow {
    try {
        collect { value -> emit(value) }
    } catch (e: HttpException) {
        Log.d("HandleHTTPError", "HttpException: ${e.response()}")
    }
}

fun <T> Flow<T>.handleConnectionErrors(): Flow<T> = flow {
    try {
        collect { value -> emit(value) }
    } catch (e: ConnectException) {
        Log.d("HandleHTTPError", "ConnectException: ${e.message}")
    }
}

/**
 * Handle Error in Response Result Success
 * In MDT Project:
 * - Code= 403, message= Incorrect phone or password! | default: Data Wrong Format
 */
//fun handleErrorResponse(code: Int, onAction: (message: String) -> Unit, onActionSuccess: () -> Unit) {
//   when(code) {
//        401 -> onAction.invoke("UnAuthorized")
//        400 -> onAction.invoke("Bad Request")
//        403 -> onAction.invoke("Incorrect phone or password!")
//        404 -> onAction.invoke("Not Found: Wrong Endpoint")
//        408 -> onAction.invoke("Timed out")
//        200 -> onActionSuccess.invoke()
//        else -> onAction.invoke("Unreachable")
//    }
//}

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

/**Handle Error in HttpException*/
fun handleHttpErrorResponse(e: Throwable): String {
    return when (e) {
        is HttpException -> parseHTTPErrorResponse(e.response()!!)

        is SocketTimeoutException ->  e.message!!

        else -> e.message!!
    }
}

fun parseHTTPErrorResponse(responseBody: Response<*>): String {
    val messageError = when(responseBody.code()) {
        400 -> "Bad Request"
        401 -> "UnAuthorized"
        404 -> "Error Not Found: Incorrect URL or Endpoint"
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