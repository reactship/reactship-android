package com.reactship.module.crashlytics

import android.app.Activity
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableArray


/**
 * Created by paveljacko on 13/12/2017.
 */

class CrashlyticsModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    var activity: Activity?

//    fun CrashlyticsModule(reactContext: ReactApplicationContext): CrashlyticsModule {
//        super(reactContext)
//        this.activity = currentActivity
//    }


    override fun getName(): String {
        return "Crashlytics"
    }

    init {
        this.activity = getCurrentActivity()
    }

    @ReactMethod
    fun crash() {
        Crashlytics.getInstance().crash()
    }

    @ReactMethod
    fun logException(value: String) {
        Crashlytics.logException(RuntimeException(value))
    }

    @ReactMethod
    fun log(message: String) {
        Crashlytics.log(message)
    }

    @ReactMethod
    fun setUserEmail(email: String) {
        Crashlytics.setUserEmail(email)
    }

    @ReactMethod
    fun setUserIdentifier(userIdentifier: String) {
        Crashlytics.setUserIdentifier(userIdentifier)
    }

    @ReactMethod
    fun setUserName(userName: String) {
        Crashlytics.setUserName(userName)
    }

    @ReactMethod
    fun setBool(key: String, value: Boolean?) {
        Crashlytics.setBool(key, value!!)
    }

    @ReactMethod
    fun setString(key: String, value: String) {
        Crashlytics.setString(key, value)
    }

    @ReactMethod
    fun setNumber(key: String, numberString: String) {
        try {
            val number = parse(numberString)
            if (number.javaClass == Double::class.java) {
                Crashlytics.setDouble(key, number.toDouble())
            } else if (number.javaClass == Float::class.java) {
                Crashlytics.setFloat(key, number.toFloat())
            } else if (number.javaClass == Int::class.java) {
                Crashlytics.setInt(key, number.toInt())
            } else if (number.javaClass == Long::class.java) {
                Crashlytics.setLong(key, number.toLong())
            }
        } catch (ex: Exception) {
            Log.e("RNFabric:", ex.message)
            ex.printStackTrace()
        }

    }

    @ReactMethod
    fun recordCustomExceptionName(name: String, reason: String, frameArray: ReadableArray) {
        val stackTrace = arrayOfNulls<StackTraceElement>(frameArray.size())
        for (i in 0 until frameArray.size()) {
            val map = frameArray.getMap(i)
            val functionName = if (map.hasKey("functionName")) map.getString("functionName") else "Unknown Function"
            val stack = StackTraceElement(
                    "",
                    functionName,
                    map.getString("fileName"),
                    if (map.hasKey("lineNumber")) map.getInt("lineNumber") else -1
            )
            stackTrace[i] = stack
        }
        val e = Exception(name + "\n" + reason)
        e.stackTrace = stackTrace
        Crashlytics.logException(e)
    }

    private fun parse(str: String): Number {
        var number: Number = 0

        if (str.contains(".")) {
            try {
                number = java.lang.Double.parseDouble(str)
            } catch (e: NumberFormatException) {
                number = java.lang.Float.parseFloat(str)
            }

        } else {
            try {
                number = Integer.parseInt(str)
            } catch (e2: NumberFormatException) {
                try {
                    number = java.lang.Long.parseLong(str)
                } catch (e3: NumberFormatException) {
                    throw e3
                }

            }

        }
        return number
    }
}