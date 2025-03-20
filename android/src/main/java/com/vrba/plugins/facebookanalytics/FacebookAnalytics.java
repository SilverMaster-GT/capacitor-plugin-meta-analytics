package com.vrba.plugins.facebookanalytics;

import android.os.Bundle;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.PluginMethod;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Iterator;

@CapacitorPlugin(name = "FacebookAnalytics")
public class FacebookAnalytics extends Plugin {
    private AppEventsLogger logger;

    @Override
    public void load() {
        logger = AppEventsLogger.newLogger(getContext());
        super.load();
    }

    @PluginMethod
    public void setAdvertiserTrackingEnabled(PluginCall call) {
        call.resolve();
    }

    @PluginMethod
    public void logEvent(PluginCall call) {
        String event = call.getString("event");
        if (event == null) {
            call.reject("Must provide an event");
            return;
        }

        JSObject params = call.getObject("params", new JSObject());
        Double valueToSum = call.getDouble("valueToSum");

        Bundle parameters = new Bundle();
        for (Iterator<String> iter = params.keys(); iter.hasNext();) {
            String key = iter.next();
            parameters.putString(key, params.getString(key));
        }

        if (valueToSum != null) {
            logger.logEvent(event, valueToSum, parameters);
        } else {
            logger.logEvent(event, parameters);
        }

        call.resolve();
    }

    @PluginMethod
    public void logPurchase(PluginCall call) {
        Double amount = call.getDouble("amount");
        String curr = call.getString("currency");

        if (amount == null || curr == null) {
            call.reject("Must provide an amount and currency");
            return;
        }

        JSObject params = call.getObject("params", new JSObject());
        Bundle parameters = new Bundle();
        for (Iterator<String> iter = params.keys(); iter.hasNext();) {
            String key = iter.next();
            parameters.putString(key, params.getString(key));
        }

        Currency currency = Currency.getInstance(curr);
        logger.logPurchase(BigDecimal.valueOf(amount), currency, parameters);

        call.resolve();
    }

    @PluginMethod
    public void logAddPaymentInfo(PluginCall call) {
        Integer success = call.getInt("success");
        Bundle params = new Bundle();
        params.putInt(AppEventsConstants.EVENT_PARAM_SUCCESS, success);
        logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_PAYMENT_INFO, params);
        call.resolve();
    }

    @PluginMethod
    public void logAddToCart(PluginCall call) {
        Double amount = call.getDouble("amount");
        String currency = call.getString("currency");

        if (amount == null || currency == null) {
            call.reject("Must provide an amount and currency");
            return;
        }

        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currency);
        logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_CART, amount, params);
        call.resolve();
    }

    @PluginMethod
    public void logCompleteRegistration(PluginCall call) {
        JSObject params = call.getObject("params", new JSObject());
        Bundle parameters = new Bundle();
        for (Iterator<String> iter = params.keys(); iter.hasNext();) {
            String key = iter.next();
            parameters.putString(key, params.getString(key));
        }
        logger.logEvent(AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION, parameters);
        call.resolve();
    }

    @PluginMethod
    public void logInitiatedCheckout(PluginCall call) {
        Double amount = call.getDouble("amount");
        if (amount == null) {
            call.reject("Must provide an amount");
            return;
        }

        JSObject params = call.getObject("params", new JSObject());
        Bundle parameters = new Bundle();
        for (Iterator<String> iter = params.keys(); iter.hasNext();) {
            String key = iter.next();
            parameters.putString(key, params.getString(key));
        }

        logger.logEvent(AppEventsConstants.EVENT_NAME_INITIATED_CHECKOUT, amount, parameters);
        call.resolve();
    }
}
