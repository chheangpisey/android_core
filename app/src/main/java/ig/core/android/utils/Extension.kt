package ig.core.android.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
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

@SuppressLint("StaticFieldLeak")
var loadingViewParent: ViewGroup? = null
var loadingView: LoadingView? = null

fun Activity.showLoading(alpha: Float? = 0.3f) {
    loadingView = loadingView ?: LoadingView(this).show(this, alpha, loadingViewParent)
}

fun Fragment.showLoading(alpha: Float? = 0.3f) {
    loadingView = loadingView ?: LoadingView(requireContext()).show(requireActivity(), alpha, loadingViewParent)
}

fun hideLoading() {
    loadingView?.let{
        it.hide()
        loadingView = null
    }
}