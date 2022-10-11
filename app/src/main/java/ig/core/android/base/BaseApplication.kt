package ig.core.android.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp
import ig.core.android.BuildConfig
import net.gotev.uploadservice.UploadService

/****
 *
 * Created by ORN TONY on 11/22/17.
 *
 */

@HiltAndroidApp
class BaseApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID
        context = applicationContext
    }

    fun getAppContext(): Context? {
        return context
    }
}