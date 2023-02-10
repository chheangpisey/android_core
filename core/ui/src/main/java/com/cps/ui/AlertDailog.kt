package com.cps.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface

/**
 * @author piseychheang
 * Created on 1/17/23 at 2:55 PM
 * Modified By piseychheang on 1/17/23 at 2:55 PM
 * File name: AlertDailog.kt
 */

fun Activity.onAlertMessage(
    title: String?,
    message: String?,
    onBtnOkClick: (() -> Unit)? = null
) {
    this.runOnUiThread {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { _: DialogInterface, _: Int ->
                onBtnOkClick?.invoke()
            }
            .show()
    }
}
