package com.reactship.module.answers

import android.app.Activity
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.*
import java.lang.reflect.Array.getDouble
import com.facebook.react.bridge.*
import java.math.BigDecimal
import java.util.*


/**
 * Created by paveljacko on 13/12/2017.
 */

class AnswersModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    var activity: Activity?

//    fun AnswersModule(reactContext: ReactApplicationContext): AnswersModule {
//        super(reactContext)
//        this.activity = currentActivity
//    }

    override fun getName(): String {
        return "Answers"
    }

    init {
        this.activity = getCurrentActivity()
    }

    @ReactMethod
    fun logAddToCart(itemPrice: String?, currency: String?, itemName: String?, itemType: String?, itemId: String?, customAttributes: ReadableMap?) {

        val event = AddToCartEvent()
        if (currency != null)
            event.putCurrency(Currency.getInstance(currency))
        if (itemPrice != null)
            event.putItemPrice(BigDecimal(itemPrice))
        if (itemName != null)
            event.putItemName(itemName)
        if (itemType != null)
            event.putItemType(itemType)
        if (itemId != null)
            event.putItemId(itemId)
        addCustomAttributes(event, customAttributes)
        Answers.getInstance().logAddToCart(event)
    }

    @ReactMethod
    fun logContentView(contentName: String?, contentType: String?, contentId: String?, customAttributes: ReadableMap?) {
        val event = ContentViewEvent()
        if (contentId != null)
            event.putContentId(contentId)
        if (contentType != null)
            event.putContentType(contentType)
        if (contentName != null)
            event.putContentName(contentName)

        addCustomAttributes(event, customAttributes)
        Answers.getInstance().logContentView(event)
    }

    @ReactMethod
    fun logCustom(eventName: String, customAttributes: ReadableMap?) {
        val event = CustomEvent(eventName)
        addCustomAttributes(event, customAttributes)
        Answers.getInstance().logCustom(event)
    }

    @ReactMethod
    fun logInvite(method: String, customAttributes: ReadableMap?) {
        val event = InviteEvent()
        event.putMethod(method)
        addCustomAttributes(event, customAttributes)
        Answers.getInstance().logInvite(event)
    }

    @ReactMethod
    fun logLevelStart(levelName: String, customAttributes: ReadableMap?) {
        val event = LevelStartEvent()
        event.putLevelName(levelName)
        addCustomAttributes(event, customAttributes)
        Answers.getInstance().logLevelStart(event)
    }

    @ReactMethod
    fun logLevelEnd(levelName: String?, score: String?, success: Boolean, customAttributes: ReadableMap?) {
        val event = LevelEndEvent()
        if (levelName != null)
            event.putLevelName(levelName)

        event.putSuccess(success)

        if (score != null)
            event.putScore(java.lang.Double.valueOf(score))

        addCustomAttributes(event, customAttributes)
        Answers.getInstance().logLevelEnd(event)
    }

    @ReactMethod
    fun logLogin(method: String, success: Boolean, customAttributes: ReadableMap?) {
        val event = LoginEvent()
        event.putMethod(method)
        event.putSuccess(success)
        addCustomAttributes(event, customAttributes)
        Answers.getInstance().logLogin(event)
    }

    @ReactMethod
    fun logPurchase(itemPrice: String?, currency: String?, success: Boolean, itemName: String?, itemType: String?, itemId: String?, customAttributes: ReadableMap?) {
        val event = PurchaseEvent()

        if (currency != null)
            event.putCurrency(Currency.getInstance(currency))
        if (itemPrice != null)
            event.putItemPrice(BigDecimal(itemPrice))
        if (itemName != null)
            event.putItemName(itemName)
        if (itemType != null)
            event.putItemType(itemType)
        if (itemId != null)
            event.putItemId(itemId)

        event.putSuccess(success)
        addCustomAttributes(event, customAttributes)
        Answers.getInstance().logPurchase(event)
    }

    @ReactMethod
    fun logRating(rating: String, contentId: String?, contentType: String?, contentName: String?, customAttributes: ReadableMap?) {
        val event = RatingEvent()
        event.putRating(Integer.valueOf(rating)!!)

        if (contentId != null)
            event.putContentId(contentId)
        if (contentType != null)
            event.putContentType(contentType)
        if (contentName != null)
            event.putContentName(contentName)

        addCustomAttributes(event, customAttributes)
        Answers.getInstance().logRating(event)
    }

    @ReactMethod
    fun logSearch(query: String, customAttributes: ReadableMap?) {
        val event = SearchEvent()
        event.putQuery(query)
        addCustomAttributes(event, customAttributes)
        Answers.getInstance().logSearch(event)
    }

    @ReactMethod
    fun logShare(method: String, contentName: String?, contentType: String?, contentId: String?, customAttributes: ReadableMap?) {
        val event = ShareEvent()
        event.putMethod(method)
        if (contentId != null)
            event.putContentId(contentId)
        if (contentType != null)
            event.putContentType(contentType)
        if (contentName != null)
            event.putContentName(contentName)
        addCustomAttributes(event, customAttributes)
        Answers.getInstance().logShare(event)
    }

    @ReactMethod
    fun logSignUp(method: String, success: Boolean, customAttributes: ReadableMap?) {
        val event = SignUpEvent()
        event.putMethod(method)
        event.putSuccess(success)
        addCustomAttributes(event, customAttributes)
        Answers.getInstance().logSignUp(event)
    }

    @ReactMethod
    fun logStartCheckout(totalPrice: String?, count: String?, currency: String?, customAttributes: ReadableMap?) {
        val event = StartCheckoutEvent()
        if (currency != null)
            event.putCurrency(Currency.getInstance(currency))
        if (count != null)
            event.putItemCount(Integer.valueOf(count)!!)
        if (totalPrice != null)
            event.putTotalPrice(BigDecimal(totalPrice))

        addCustomAttributes(event, customAttributes)
        Answers.getInstance().logStartCheckout(event)
    }

    private fun addCustomAttributes(event: AnswersEvent<*>, attributes: ReadableMap?) {
        if (attributes != null) {
            val itr = attributes!!.keySetIterator()
            while (itr.hasNextKey()) {
                val key = itr.nextKey()

                val type = attributes!!.getType(key)
                when (type) {
                    ReadableType.Boolean -> event.putCustomAttribute(key, attributes!!.getBoolean(key).toString())
                    ReadableType.Number -> event.putCustomAttribute(key, attributes!!.getDouble(key))
                    ReadableType.String -> event.putCustomAttribute(key, attributes!!.getString(key))
                    ReadableType.Null -> {
                    }
                    else -> Log.e("ReactNativeFabric", "Can't add objects or arrays")
                }
            }
        }
    }
}