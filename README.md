# PermissionK

Opinionated permission handling done right with Kotlin.

## Why use it

- The whole flow has been sorted out.
- Multiple permissions supported.
- Nice DSL for better code.
- Written in Kotlin

## What is the handling flow

1. Show an explanation to the user why we need those permissions.
2. If the user press ok, then we will request for the permissions.
3. After getting the request result, we will handling 3 cases:
    - When everything is fine
    - When permissions not accepted
    - When user checks the `Never Ask again` option, we will route the user to the application settings to enable the permission there.
    
> No `shouldShowRationale`? Yes, this is such a confusing method depends on its name. In our flow, user always gets the explanation first, which should be a design which you always follow. But underneath, we use it to check the `NeverAskAgain` option.


## How to use it

1. Install (Not yet published)

```groovy
implementation 'xyz.albertgao:permissionk:1.0.0'
```

2. Trigger the explanation step to invoking this function inside your activity, properly a onClickHandler

```groovy
startToRequestPermission {
    permissions = listOf(SEND_SMS, READ_PHONE_STATE)
    requestCode = this@MyActivity.requestCode

    ifSuccess {
        // Your handling logic
    }

    elseShowDialogToRequestPermission{
        message = "Hi, We need these permissions because we want to make the user experiences better, please grant them!"
    }
}
```

3. Inside the onRequestPermissionResult method, by using the following DSL:

```kotlin
override fun onRequestPermissionsResult(
    theRequestCode: Int,
    thePermissions: Array<String>,
    theGrantResults: IntArray
) {
    onRequestPermissionResultHandler {
        actualRequestCode = theRequestCode
        expectRequestCode = this@MyActivity.requestCode
        permissions = thePermissions
        grantResultsParam = theGrantResults


        onSuccess {
            // Do what you want
        }

        onFailure {
            // You don't get the permission, do something
        }

        onNeverAskAgain {
            message = "We noticed you have disabled some permissions . We will take you to the Application settings, you can re-enable the permission there."
        }
    }
}
```

