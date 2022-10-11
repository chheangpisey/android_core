package ig.core.android.service.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import ig.core.android.utils.AppConstant

@Suppress("UNREACHABLE_CODE")
class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        when (intent!!.action) {
            AppConstant.CORE_ANDROID -> {
                Log.d("xxx","Broadcast Receiver")
            }
        }
    }
}