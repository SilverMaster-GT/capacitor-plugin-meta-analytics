'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

const FacebookAnalytics = core.registerPlugin('FacebookAnalytics', {
    web: () => Promise.resolve().then(function () { return web; }).then(module => new module.FacebookAnalyticsWeb())
});

class FacebookAnalyticsWeb extends core.WebPlugin {
    constructor() {
        super({
            name: 'FacebookAnalytics',
            platforms: ['web'],
        });
    }
    setAdvertiserTrackingEnabled(_) {
        throw super.unimplemented();
    }
    logEvent(_) {
        throw super.unimplemented();
    }
    logPurchase(_) {
        throw super.unimplemented();
    }
    logAddPaymentInfo(_) {
        throw super.unimplemented();
    }
    logAddToCart(_) {
        throw super.unimplemented();
    }
    logCompleteRegistration(_) {
        throw super.unimplemented();
    }
    logInitiatedCheckout(_) {
        throw super.unimplemented();
    }
}

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    FacebookAnalyticsWeb: FacebookAnalyticsWeb
});

exports.FacebookAnalytics = FacebookAnalytics;
//# sourceMappingURL=plugin.cjs.js.map
