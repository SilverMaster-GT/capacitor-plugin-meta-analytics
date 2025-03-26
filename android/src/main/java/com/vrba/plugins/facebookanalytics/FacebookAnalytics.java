package com.vrba.plugins.facebookanalytics;

// Debug Only
// import com.facebook.FacebookSdk;
// import com.facebook.LoggingBehavior;

import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.getcapacitor.JSObject;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Iterator;
import android.util.Log;

import android.os.Bundle;

@CapacitorPlugin(name = "FacebookAnalytics")
public class FacebookAnalytics extends Plugin {
    private AppEventsLogger logger;

    @Override
    public void load() {
        super.load();
        // FacebookSdk.setIsDebugEnabled(true); // Habilita el modo de depuración
        // FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS); // Habilita los registros de eventos de la aplicación
        logger = AppEventsLogger.newLogger(getContext());
    }

    @PluginMethod
    public void setAdvertiserTrackingEnabled(PluginCall call) {
        call.resolve();
    }

    @PluginMethod
    public void logEvent(PluginCall call) {
        String event = call.getString("event");
        if (event == null || event.isEmpty()) {
            call.reject("Must provide an event");
            return;
        }

        JSObject params = call.getObject("params", new JSObject());
        Double valueToSum = call.getDouble("valueToSum");
        Bundle parameters = new Bundle();

        // Auditoría de parámetros
        Log.d("logEvent", "Auditoría de parámetros:");
        Log.d("logEvent", "Evento: " + event);
        Log.d("logEvent", "Params: " + params.toString());
        Log.d("logEvent", "ValueToSum: " + valueToSum);

        if (params != null && params.length() > 0) {
            Iterator<String> keys = params.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = params.getString(key);
                parameters.putString(key, value);
            }
        }

         // Auditoría del Bundle antes de enviar al SDK de Meta
        Log.d("logEvent", "Auditoría del Bundle:");
        if (parameters != null) {
            for (String key : parameters.keySet()) {
                Log.d("logEvent", "Bundle Key: " + key + ", Value: " + parameters.get(key));
            }
        }

        if (valueToSum != null) {
            logger.logEvent(event, valueToSum, parameters);
        } else {
            logger.logEvent(event, parameters);
        }
        logger.flush();
        call.resolve();
    }

    @PluginMethod
    public void logPurchase(PluginCall call) {
        Double amount = call.getDouble("amount");
        String currencyCode  = call.getString("currency");

        if (amount == null || currencyCode == null || currencyCode.isEmpty()) {
            call.reject("Must provide an amount and currency");
            return;
        }

        Currency currency = Currency.getInstance(currencyCode);
        JSObject params = call.getObject("params", new JSObject());
        Bundle parameters = new Bundle();

        if (params != null && params.length() > 0) {
            Iterator<String> keys = params.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = params.getString(key);
                parameters.putString(key, value);
            }
        }
        
        logger.logPurchase(BigDecimal.valueOf(amount), currency, parameters);
        call.resolve();
    }

    @PluginMethod
    public void logAddPaymentInfo(PluginCall call) {
        Integer success = call.getInt("success");
        Bundle params = new Bundle();
        params.putInt(AppEventsConstants.EVENT_PARAM_SUCCESS, success != null ? success : 0);
        logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_PAYMENT_INFO, params);
        call.resolve();
    }

    @PluginMethod
    public void logAddToCart(PluginCall call) {
        Double amount = call.getDouble("amount", null);
        String currencyCode  = call.getString("currency");

        if (amount == null || currencyCode == null || currencyCode.isEmpty()) {
            call.reject("Must provide an amount and currency");
            return;
        }
        
        Bundle params = new Bundle();
        params.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, currencyCode);
        logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_CART, amount, params);

        call.resolve();
    }

    @PluginMethod
    public void logCompleteRegistration(PluginCall call) {
        JSObject params = call.getObject("params", new JSObject());
        Bundle parameters = new Bundle();

        if (params != null && params.length() > 0) {
            Iterator<String> keys = params.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = params.getString(key);
                parameters.putString(key, value);
            }
        }

        logger.logEvent(AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION, parameters);
        call.resolve();
    }

    @PluginMethod
    public void logInitiatedCheckout(PluginCall call) {
        Double amount = call.getDouble("amount", null);

        if (amount == null) {
            call.reject("Must provide an amount");
            return;
        }
        
        JSObject params = call.getObject("params", new JSObject());
        Bundle parameters = new Bundle();

        if (params.length() > 0) {
            Iterator<String> keys = params.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = params.getString(key);
                parameters.putString(key, value);
            }
        }
        
        logger.logEvent(AppEventsConstants.EVENT_NAME_INITIATED_CHECKOUT, amount, parameters);
        call.resolve();
    }
}
