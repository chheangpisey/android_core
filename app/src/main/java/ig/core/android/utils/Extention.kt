package ig.core.android.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import ig.core.android.R
import org.jetbrains.anko.runOnUiThread

fun alertMessage(
    context: Context,
    title: String?,
    message: String?,
    onBtnOkClick: (() -> Unit)? = null
) {
    val builder = android.app.AlertDialog.Builder(context)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setCancelable(false)
    builder.setPositiveButton("OK") { dialog, _ ->
        onBtnOkClick?.invoke()
        dialog.dismiss()
    }
    val dialog: android.app.AlertDialog = builder.create()
    dialog.show()
}

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


fun setWindowShowScreen(dialog: Dialog) {
    val window = dialog.window
    window!!.setBackgroundDrawableResource(R.color.cardview_light_background)
    window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
}

fun snackBar(view: View, string: String) {
    Snackbar.make(view, string, Snackbar.LENGTH_SHORT).show()
}