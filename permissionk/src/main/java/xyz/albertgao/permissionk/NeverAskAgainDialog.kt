package xyz.albertgao.permissionk

class NeverAskAgainDialog(
    var title:String = "Notice",
    var message:String = "We noticed you have disable our permission, " +
        "we will take you to the settings page to re-enable it",
    var positiveButtonText:String = "Take me there",
    var negativeButtonText:String = "No"
)