import { WebPlugin } from '@capacitor/core';
import type { FacebookAnalyticsPlugin } from './definitions';
export declare class FacebookAnalyticsWeb extends WebPlugin implements FacebookAnalyticsPlugin {
    constructor();
    setAdvertiserTrackingEnabled(_: {
        enabled: boolean;
    }): Promise<string>;
    logEvent(_: {
        event: string;
        valueToSum?: number;
        params?: any;
    }): Promise<string>;
    logPurchase(_: {
        amount: number;
        params?: any;
        currency: string;
    }): Promise<string>;
    logAddPaymentInfo(_: {
        success: number;
    }): Promise<string>;
    logAddToCart(_: {
        amount: number;
        currency: string;
        params?: any;
    }): Promise<string>;
    logCompleteRegistration(_: {
        params?: any;
    }): Promise<string>;
    logInitiatedCheckout(_: {
        amount: number;
        params?: any;
    }): Promise<string>;
}
