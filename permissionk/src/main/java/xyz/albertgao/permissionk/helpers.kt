package xyz.albertgao.permissionk

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

fun Activity.showPermissionReasonAndRequest(
    title: String,
    message: String,
    permissions: Array<String>,
    requestCode: Int
) {
    alert(
        message,
        title
    ) {
        yesButton {
            ActivityCompat.requestPermissions(
                this@showPermissionReasonAndRequest,
                permissions,
                requestCode
            )
        }
        noButton { }
    }.show()
}

fun Context.isPermissionGranted(permission:String):Boolean =
    ContextCompat.checkSelfPermission(this, permission) ==
        PackageManager.PERMISSION_GRANTED

