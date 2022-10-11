package ig.core.android.utils.session

import android.annotation.SuppressLint
import android.content.Context
import ig.core.android.base.BaseSession
import ig.core.android.service.model.LoginResponse
import ig.core.android.utils.AppConstant

class UserSession(val context: Context) : BaseSession(context) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: UserSession? = null
        fun getInstance(context: Context): UserSession {
            if (instance == null)
                instance = UserSession(context)
            return instance!!
        }
    }

    override val preferenceName: String
        get() = "user"

    fun setUsername(value: String) {
        save(AppConstant.USERNAME, value)
    }

    fun getUsername() : String {
        return get(AppConstant.USERNAME,"").toString()
    }

    fun setPassword(value: String) {
        save(AppConstant.PASSWORD, value)
    }

    fun getPassword() : String {
        return get(AppConstant.PASSWORD,"").toString()
    }

    fun mapData(data: LoginResponse) : UserSession {
        setUsername(data.message!!)
        setPassword(data.message!!)
        return this
    }

}