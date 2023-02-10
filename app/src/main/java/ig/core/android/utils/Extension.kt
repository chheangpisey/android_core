package ig.core.android.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import org.jetbrains.anko.runOnUiThread

/**
 * @author piseychheang
 * Created on 2/9/23 at 3:31 PM
 * Modified By piseychheang on 2/9/23 at 3:31 PM
 * File name: Extension.kt
 */


fun onAlertMessage(
    context: Context,
    title: String?,
    message: String?,
    onBtnOkClick: (() -> Unit)? = null
) {
    context.runOnUiThread {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { _: DialogInterface, _: Int ->
                onBtnOkClick?.invoke()
            }
            .show()
    }
}