package com.mdlink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.mdlink.model.BookAppointmentRequest;
import com.mdlink.model.CreateOrederRequest;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mdlink.util.PaypalConfigManager.REQUEST_CODE_PAYMENT;
import static com.mdlink.util.PaypalConfigManager.config;


public class ConfirmAppointmentActivity extends BaseActivity implements View.OnClickListener {
    private String TAG = getClass().getSimpleName();
    private Toolbar toolbar;
    private TextView txtRenewApptForCA,txtSicknoteApptForCA,txtApptTypeCA, txtNameCA, txtAgeCA, txtPurposeCA, txtPreviousHospitalCA,
            txtAllergiesCA, txtMedicalConditionCA, txtPharmacyCA, txtLocationCA, txtDateCA, txtTimeCA,
            txtPreferredDoctorCA, txtPayByPaypalCA;
    private String AppointmentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_appt);
        initToolbar();
        initViews();
        bindViews();
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWhite));
        setUpToolbar(toolbar, R.color.colorAccent);
        setToolbarTitle(getString(R.string.label_confirmappt), R.color.colorAccent);
    }

    private void initViews() {

        txtRenewApptForCA = findViewById(R.id.txtRenewApptForCA);
        txtSicknoteApptForCA = findViewById(R.id.txtSicknoteApptForCA);

        txtApptTypeCA = findViewById(R.id.txtApptTypeCA);
        txtNameCA = findViewById(R.id.txtNameCA);
        txtAgeCA = findViewById(R.id.txtAgeCA);
        txtPurposeCA = findViewById(R.id.txtPurposeCA);
        txtAllergiesCA = findViewById(R.id.txtAllergiesCA);
        txtPreviousHospitalCA = findViewById(R.id.txtPreviousHospitalCA);
        txtMedicalConditionCA = findViewById(R.id.txtMedicalConditionCA);
        txtPharmacyCA = findViewById(R.id.txtPharmacyCA);
        txtLocationCA = findViewById(R.id.txtLocationCA);
        txtDateCA = findViewById(R.id.txtDateCA);
        txtTimeCA = findViewById(R.id.txtTimeCA);
        txtPreferredDoctorCA = findViewById(R.id.txtPreferredDoctorCA);
        txtPayByPaypalCA = findViewById(R.id.txtPayByPaypalCA);
        txtPayByPaypalCA.setOnClickListener(this);
    }

    private void bindViews() {
        if(null != getIntent()){
            AppointmentId = getIntent().getStringExtra("AppointmentId");
            String preferredDoctorName = getIntent().getStringExtra("PreferredDoctorName");
            BookAppointmentRequest bookAppointmentRequest = (BookAppointmentRequest)getIntent().getSerializableExtra("BookAppointmentRequest");
            Log.i(TAG,"name>>>>>>>>>>>"+bookAppointmentRequest.getName());
            Log.i(TAG,"preferredDoctorName>>>>>>>>>>>"+preferredDoctorName);
            txtSicknoteApptForCA.setText(getString(R.string.i_would_to_obtain_sicknote, bookAppointmentRequest.getSickNote().equalsIgnoreCase("1") ? "Yes":"No"));
            txtRenewApptForCA.setText(getString(R.string.i_would_to_renew_refill_a_prescription, bookAppointmentRequest.getIsRenew().equalsIgnoreCase("1") ? "Yes":"No" ));
            txtNameCA.setText(getString(R.string.colon, bookAppointmentRequest.getName()));
            txtAgeCA.setText(getString(R.string.colon,bookAppointmentRequest.getAge()));
            txtPurposeCA.setText(getString(R.string.colon,bookAppointmentRequest.getVisitPurpose()));
            txtPreviousHospitalCA.setText(getString(R.string.colon,bookAppointmentRequest.getPreviousHospital()));
            if(!TextUtils.isEmpty(bookAppointmentRequest.getMedicalConditions())){
                txtMedicalConditionCA.setText(getString(R.string.medicalcondition, bookAppointmentRequest.getMedicalConditions().substring(0, bookAppointmentRequest.getMedicalConditions().length() - 1)));
            }else {
                txtMedicalConditionCA.setText(getString(R.string.medicalcondition,""));
            }
            txtPharmacyCA.setText(getString(R.string.colon,bookAppointmentRequest.getPharmacy()));
            txtLocationCA.setText(getString(R.string.colon,bookAppointmentRequest.getLocation()));
            txtDateCA.setText(getString(R.string.colon,bookAppointmentRequest.getScheduledDate()));
            txtTimeCA.setText(getString(R.string.colon,bookAppointmentRequest.getScheduledTime()));
            txtPreferredDoctorCA.setText(getString(R.string.colon,preferredDoctorName));
            txtAllergiesCA.setText(getString(R.string.colon,bookAppointmentRequest.getAllergy()));
            String type ="";
            switch (bookAppointmentRequest.getType()) {
                case "1":
                    type = "Audio ($12)";
                    break;
                case "2":
                    type = "Instant Message ($12)";
                    break;
                case "3":
                    type = "Video Call ($15)";
                    break;
            }
            txtApptTypeCA.setText(getString(R.string.colon,type));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtPayByPaypalCA:
                callPaymentGatewayAPI();
                break;
        }
    }

    private void callPaymentGatewayAPI() {
        initialPaypalService();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 200ms
                onBuyPressed(null);
            }
        }, 200);
    }

    private void initialPaypalService(){
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
    }

    public void onBuyPressed(View pressed) {
        /*
         * PAYMENT_INTENT_SALE will cause the payment to complete immediately.
         * Change PAYMENT_INTENT_SALE to
         *   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
         *   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
         *     later via calls from your server.
         *
         * Also, to include additional payment details and an item list, see getStuffToBuy() below.
         */
        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);

        /*
         * See getStuffToBuy(..) for examples of some available payment options.
         */

        Intent intent = new Intent(ConfirmAppointmentActivity.this, PaymentActivity.class);
        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    private PayPalPayment getThingToBuy(String paymentIntent) {
        return new PayPalPayment(new BigDecimal("0.01"), "USD", "sample item",
                paymentIntent);
    }

    protected void displayResultText(String result) {
        //((TextView)findViewById(R.id.txtResult)).setText("Result : " + result);
        Toast.makeText(
                getApplicationContext(),
                result, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i(TAG, "1>>>>>>"+confirm.toJSONObject().toString(4));
                        Log.i(TAG, "2>>>>>>"+confirm.getPayment().toJSONObject().toString(4));
                        /**
                         *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
                         * or consent completion.
                         * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                         * for more details.
                         *
                         * For sample mobile backend interactions, see
                         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
                         */
                        CreateOrederRequest createOrederRequest = new CreateOrederRequest();

                        JSONObject jsonObject = new JSONObject(confirm.toJSONObject().toString(4));
                        String transactionId = jsonObject.getJSONObject("response").getString("id");
                        String state = jsonObject.getJSONObject("response").getString("state");
                        createOrederRequest.setAppId(Integer.parseInt(AppointmentId));
                        createOrederRequest.setCouponCode("NOCODE");
                        createOrederRequest.setTransactionId(transactionId);
                        createOrederRequest.setTransactionStatus(state);
                        createOrederRequest.setTransactionResponse(state);

                        callCreatOrderAPI(createOrederRequest);
                        displayResultText("PaymentConfirmation info received from PayPal");

                    } catch (JSONException e) {
                        Log.e(TAG, "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i(TAG, "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        TAG,
                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

        /**
         * TODO: Send the authorization response to your server, where it can
         * exchange the authorization code for OAuth access and refresh tokens.
         *
         * Your server must then store these tokens, so that your server code
         * can execute payments for this user in the future.
         *
         * A more complete example that includes the required app-server to
         * PayPal-server integration is available from
         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
         */

    }

    private void callCreatOrderAPI(final CreateOrederRequest createOrederRequest) {
        Call<JsonObject> callToGetUserProfile = App.apiService.createOreder(createOrederRequest);
        callToGetUserProfile.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i(TAG, ">>>>>>>>>>>>>>>>>" + response.body());
                if (response.body().get("status").getAsString().equalsIgnoreCase("200")) {
                    Toast.makeText(ConfirmAppointmentActivity.this,createOrederRequest.getTransactionStatus().equalsIgnoreCase("approved") ?"Thank You!! " : " Oops " +response.body().get("message").getAsString(),Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent = new Intent(ConfirmAppointmentActivity.this, PatientPortalActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.i(TAG, ">>>>>>>>>onFailure>>>>>>>>");
            }
        });
    }

    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
