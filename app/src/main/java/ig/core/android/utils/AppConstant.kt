package ig.core.android.utils

import com.google.gson.Gson
import java.io.Reader

/****
 *
 * Created by ORN TONY on 1/16/18.
 *
 */

object AppConstant {
    var TOKEN: String? = ""
    const val USERNAME = "username"
    const val PASSWORD = "password"
    const val LANGUAGE_STATUS = "languageStatus"
    const val LANGUAGE_KEY = "languageKey"

    const val CORE_ANDROID = "ig.core.android"
    const val SIZE = 10

    const val STARTACTIVITYANIM_LEFT = 1
    const val STARTACTIVITYANIM_RIGHT = 2
    const val STARTACTIVITYANIM_TOP = 3

    const val USER_LC_READER = 5
    const val ENCRYPTION_KEY = "aesEncryptionKey"
    const val VECTOR = "encryptionIntVec"


    const val MODULE_HOME = "home"
    const val MODULE_FAVORITE = "favorite"

    fun <T> errorResponse(json: Reader, clazz: Class<T>): T {
        return Gson().fromJson(json, clazz)
    }
}