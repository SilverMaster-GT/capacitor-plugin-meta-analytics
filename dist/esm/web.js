import { WebPlugin } from '@capacitor/core';
export class FacebookAnalyticsWeb extends WebPlugin {
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
//# sourceMappingURL=web.js.map