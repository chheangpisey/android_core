package ig.core.android.utils.session

import android.content.Context
import android.util.Log
import ig.core.android.base.BaseSession
import ig.core.android.utils.AppConstant

class LanguagePrefs(val context: Context) : BaseSession(context) {
    override val preferenceName: String
        get() = "language"

    companion object {
        var instance: LanguagePrefs? = null

        fun getInstance(context: Context):LanguagePrefs{
            if (instance==null)
                instance= LanguagePrefs(context)
            return instance!!
        }
    }

    fun getLanguage(): String{
        Log.e("language", get(AppConstant.LANGUAGE_KEY, "").toString())
        return get(AppConstant.LANGUAGE_KEY,"").toString()
    }

    fun setLanguage(lang: String){
        save(AppConstant.LANGUAGE_KEY, lang)
    }

    fun getLanguageStatus(): Boolean {
        return `is`(AppConstant.LANGUAGE_STATUS, false)
    }

    fun setLanguageStatus(status: Boolean) {
        save(AppConstant.LANGUAGE_STATUS, status)
    }
}