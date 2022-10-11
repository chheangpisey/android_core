package ig.core.android.service.model.custom

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
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
 */
fun handleErrorResponse(code: Int, onAction: (message: String) -> Unit) {
    val message = when(code) {
        401 -> "UnAuthorized"
        400 -> "Bad Request"
        403 -> "Data Wrong Format"
        404 -> "Not Found: Wrong Endpoint"
        408 -> "Timed out"
        200 -> "Success"
        else -> "Unreachable"
    }

    onAction.invoke(message)
}

/**
 * Handle Error in HttpException
 */
fun handleErrorResponse(e: Throwable): String {
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