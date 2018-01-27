package com.reactship

import com.example.app.BuildConfig.DEBUG

import android.app.Application
import com.BV.LinearGradient.LinearGradientPackage
import com.RNFetchBlob.RNFetchBlobPackage

import java.util.Arrays

/**
 * Created by paveljacko on 24/01/2018.
 */

class MainApplication : Application(), ReactApplication {

    companion object {
        @JvmStatic
        private val mCallbackManager = CallbackManager.Factory.create()

        fun getCallbackManager(): CallbackManager = mCallbackManager
    }

    private val mReactNativeHost = object : ReactNativeHost(this) {
        override fun getUseDeveloperSupport(): Boolean = DEBUG

        override fun getPackages(): List<ReactPackage> {
            return Arrays.asList<ReactPackage>(
                    MainReactPackage()
            )
        }

        override fun getJSMainModuleName(): String = "index.client.android"
    }

    override fun getReactNativeHost(): ReactNativeHost = mReactNativeHost

    override fun onCreate() {
        super.onCreate()
        SoLoader.init(this, /* native exopackage */ false)
    }
}
