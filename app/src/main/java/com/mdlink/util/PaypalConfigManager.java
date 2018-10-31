package com.mdlink.util;

import android.net.Uri;

import com.paypal.android.sdk.payments.PayPalConfiguration;

public class PaypalConfigManager {
    public static final String sandbox = "AaqAVtcYQu7FqhVDYzY1NNKVqOYXWK3L7tfsl5KrSQVPkrvbX-13t1lQSZM7BE_a8K-YtspdOhRW-z_h";
    public static final String production = "ASInx3EhrrvGCw8_KPMdCQbROVvWJKSHPKMxCi2GC1E7Y0GsKIcvDRHh0q_1vFWU34D4dwa0SFrPjzU-";
   /* client: {
        sandbox: 'AaqAVtcYQu7FqhVDYzY1NNKVqOYXWK3L7tfsl5KrSQVPkrvbX-13t1lQSZM7BE_a8K-YtspdOhRW-z_h',
                production: 'ASInx3EhrrvGCw8_KPMdCQbROVvWJKSHPKMxCi2GC1E7Y0GsKIcvDRHh0q_1vFWU34D4dwa0SFrPjzU-'
    },*/

    public static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    // note that these credentials will differ between live & sandbox environments.
    public static final String CONFIG_CLIENT_ID = sandbox;

    public static final int REQUEST_CODE_PAYMENT = 1;
    public static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    public static final int REQUEST_CODE_PROFILE_SHARING = 3;

    public static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Example Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

}
