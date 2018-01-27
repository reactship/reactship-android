package com.reactship

import android.content.Intent
import android.os.Bundle
import com.facebook.react.ReactActivity
import com.facebook.appevents.AppEventsLogger
import com.facebook.FacebookSdk
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric





/**
 * Created by paveljacko on 24/01/2018.
 */

class MainActivity : ReactActivity() {
    override fun getMainComponentName(): String? = "App"

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
        Fabric.with(this, Crashlytics())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        MainApplication.getCallbackManager().onActivityResult(requestCode, resultCode, data)
    }
}
