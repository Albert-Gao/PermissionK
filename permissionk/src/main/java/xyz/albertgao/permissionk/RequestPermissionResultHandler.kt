package xyz.albertgao.permissionk

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import org.jetbrains.anko.alert



class RequestPermissionResultHandler(
    var actualRequestCode: Int = 0,
    var expectRequestCode: Int = 0,
    var permissions: Array<String> = arrayOf(""),
    var grantResultsParam: IntArray = intArrayOf(0)
) {
    private lateinit var onSuccessCallback: ()->Unit
    private lateinit var onFailureCallback: ()->Unit
    private lateinit var onNeverAskAgainDialog: NeverAskAgainDialog
    private lateinit var activity: Activity

    fun RequestPermissionResultHandler.onSuccess(callback: ()->Unit) {
        onSuccessCallback = callback
    }

    fun RequestPermissionResultHandler.onFailure(callback: ()->Unit) {
        onFailureCallback = callback
    }

    fun RequestPermissionResultHandler.onNeverAskAgain(
        init:NeverAskAgainDialog.() -> Unit
    ) {
        this.onNeverAskAgainDialog = NeverAskAgainDialog().apply(init)
    }

    internal fun addContext(activity: Activity):RequestPermissionResultHandler {
        this.activity = activity
        return this
    }

    internal fun start() {
        if (actualRequestCode != expectRequestCode) return

        // Get the permission
        if (arePermissionsGranted(grantResultsParam)) {
            onSuccessCallback()
            return
        }

        // Not getting permission - case 1 (Never Ask Again has been checked)
        if (isUserCheckNeverAskAgain()) {
            with(activity) {
                alert(
                    onNeverAskAgainDialog.message,
                    onNeverAskAgainDialog.title
                ) {
                    positiveButton(onNeverAskAgainDialog.positiveButtonText) {
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", packageName, null)
                        )
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }

                    negativeButton(onNeverAskAgainDialog.negativeButtonText) { }
                }.show()
            }
            return
        }

        // Not getting permission - case 2 (User just denied)
        onFailureCallback()
    }

    private fun isUserCheckNeverAskAgain() =
        this.permissions.all {
            !ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                it
            )
        }

    private fun arePermissionsGranted(grantResults: IntArray) =
        grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }

}
