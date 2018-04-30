package xyz.albertgao.permissionk

import android.app.Activity

fun Activity.onRequestPermissionResultHandler(
    init: RequestPermissionResultHandler.() -> Unit
) {
    RequestPermissionResultHandler()
        .addContext(this)
        .apply(init)
        .start()
}

fun Activity.startToRequestPermission(
    init: StartToRequestPermission.() -> Unit
) {
    StartToRequestPermission()
        .addContext(this)
        .apply(init)
        .start()
}
