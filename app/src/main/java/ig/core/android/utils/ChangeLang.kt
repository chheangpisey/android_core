package ig.core.android.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*

class ChangeLang {
    companion object {
        val instance = ChangeLang()
    }

    fun setLanguage(context: Context, lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}