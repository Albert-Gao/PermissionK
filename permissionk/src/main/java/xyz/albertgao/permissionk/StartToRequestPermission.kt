package xyz.albertgao.permissionk

import android.app.Activity



class StartToRequestPermission(
    var permissions: List<String> = listOf("1", "2"),
    var requestCode: Int = 0
) {
    private lateinit var ifSuccessCallback:() -> Unit
    private lateinit var activity: Activity
    private lateinit var requestPermissionDialog: RequestPermissionDialog

    fun StartToRequestPermission.ifSuccess(callback: () -> Unit) {
        this.ifSuccessCallback = callback
    }

    fun StartToRequestPermission.elseShowDialogToRequestPermission(
        callback: RequestPermissionDialog.() -> Unit
    ) {
        this.requestPermissionDialog = RequestPermissionDialog().apply(callback)
    }

    internal fun start() {
        if (areAllPermissionsGranted()) {
            ifSuccessCallback()
        } else {
            with(activity) {
                showPermissionReasonAndRequest(
                    this@StartToRequestPermission.requestPermissionDialog.title,
                    this@StartToRequestPermission.requestPermissionDialog.message,
                    this@StartToRequestPermission.permissions.toTypedArray(),
                    this@StartToRequestPermission.requestCode
                )
            }
        }
    }

    internal fun addContext(activity: Activity):StartToRequestPermission {
        this.activity = activity
        return this
    }

    private fun areAllPermissionsGranted() = this.permissions.all {
        with(activity) {
            isPermissionGranted(it)
        }
    }
}
