import Foundation
import Capacitor
import FBSDKCoreKit

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FacebookAnalytics)
public class FacebookAnalytics: CAPPlugin {

    private func JSObjectToFBParams(_ params: JSObject) -> [AppEvents.ParameterName : Any] {
        let newDict = params.reduce(into: [AppEvents.ParameterName : Any]()) { (partialResult, arg1) in
            if let newValue = arg1.value as? String {
                partialResult[AppEvents.ParameterName(arg1.key)] = newValue
            }
        }
        return newDict
    }

    @objc func setAdvertiserTrackingEnabled(_ call: CAPPluginCall) {
        guard let enabled = call.getBool("enabled") else {
            call.reject("Missing enabled argument")
            return
        }
        Settings.shared.isAdvertiserTrackingEnabled = enabled
        call.resolve()
    }

    @objc func logEvent(_ call: CAPPluginCall) {
        guard let event = call.getString("event") else {
            call.reject("Missing event argument")
            return
        }

        if let valueToSum = call.getDouble("valueToSum") {
            if let params = call.getObject("params") {
                AppEvents.shared.logEvent(AppEvents.Name(event), valueToSum: valueToSum, parameters: JSObjectToFBParams(params))
            } else {
                AppEvents.shared.logEvent(AppEvents.Name(event), valueToSum: valueToSum)
            }
        } else {
            if let params = call.getObject("params") {
                AppEvents.shared.logEvent(AppEvents.Name(event), parameters: JSObjectToFBParams(params))
            } else {
                AppEvents.shared.logEvent(AppEvents.Name(event))
            }
        }
        call.resolve()
    }

    @objc func logPurchase(_ call: CAPPluginCall) {
        guard let amount = call.getDouble("amount") else {
            call.reject("Missing amount argument")
            return
        }

        let currency = call.getString("currency") ?? "USD"
        let params = call.getObject("params") ?? [String: String]()

        AppEvents.shared.logPurchase(amount: amount, currency: currency, parameters: JSObjectToFBParams(params))
        call.resolve()
    }

    @objc func logAddPaymentInfo(_ call: CAPPluginCall) {
        let success = call.getInt("success") ?? 0
        AppEvents.shared.logEvent(.addedPaymentInfo, parameters: [AppEvents.ParameterName("success"): success])
        call.resolve()
    }

    @objc func logAddToCart(_ call: CAPPluginCall) {
        guard let amount = call.getDouble("amount") else {
            call.reject("Missing amount argument")
            return
        }
        let currency = call.getString("currency") ?? "USD"

        var params = call.getObject("params") ?? [String: String]()
        params[AppEvents.ParameterName.currency.rawValue] = currency

        AppEvents.shared.logEvent(.addedToCart, valueToSum: amount, parameters: JSObjectToFBParams(params))
        call.resolve()
    }

    @objc func logCompleteRegistration(_ call: CAPPluginCall) {
        let params = call.getObject("params") ?? [String: String]()
        AppEvents.shared.logEvent(.completedRegistration, parameters: JSObjectToFBParams(params))
        call.resolve()
    }

    @objc func logInitiatedCheckout(_ call: CAPPluginCall) {
        guard let amount = call.getDouble("amount") else {
            call.reject("Missing amount argument")
            return
        }

        let params = call.getObject("params") ?? ["default": "default"]
        AppEvents.shared.logEvent(.initiatedCheckout, valueToSum: amount, parameters: JSObjectToFBParams(params))
        call.resolve()
    }
}