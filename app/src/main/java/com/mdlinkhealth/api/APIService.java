package com.mdlinkhealth.api;

import com.google.gson.JsonObject;
import com.mdlinkhealth.model.AddPrescriptionDoctorSide;
import com.mdlinkhealth.model.AppointmentListResponse;
import com.mdlinkhealth.model.AppointmentOptionsResponse;
import com.mdlinkhealth.model.BookAppointmentRequest;
import com.mdlinkhealth.model.CreateOrderRequest;
import com.mdlinkhealth.model.DoctorPortalResponse;
import com.mdlinkhealth.model.DoctorProfile;
import com.mdlinkhealth.model.DoctorsListModel;
import com.mdlinkhealth.model.PatientProfileRequest;
import com.mdlinkhealth.model.PharmacyResponse;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    @Multipart
    @POST("doctor")
    Call<DoctorPortalResponse> postDoctorRequest(
            @Part("email") RequestBody email,
            @Part("name") RequestBody name,
            @Part("phone_no") RequestBody phone_no,
            @Part("age") RequestBody age,
            @Part("qualification") RequestBody qualification,
            @Part("speciality") RequestBody speciality,
            @Part("medical_school") RequestBody medical_school,
            @Part("medical_council") RequestBody medical_council,
            @Part("graduation_year") RequestBody graduation_year,
            @Part("registration_number") RequestBody registration_number,
            @Part("location") RequestBody location,
            @Part("password") RequestBody password,
            @Part("confirmpassword") RequestBody confirmpassword,
            @Part("available_day") RequestBody available_day,
            @Part("avail_morning") RequestBody avail_morning,
            @Part("avail_morning_to") RequestBody avail_morning_to,
            @Part("avail_evening") RequestBody avail_evening,
            @Part("avail_evening_to") RequestBody avail_evening_to,
            @Part("terms_and_cond") RequestBody terms_and_cond,
            @Part("userID") RequestBody userID,
            @Part("role_id") RequestBody role_id,
            @Part("created_at") RequestBody created_at,
            @Part MultipartBody.Part signature,
            @Part MultipartBody.Part medical_certificate,
            @Part("device_token") RequestBody device_token,
            @Part("device_type") RequestBody device_type
    );

    @POST("availabledoctors")
    Call<DoctorsListModel> availabledoctors(@Body HashMap jsonObject);

    @POST("appointment")
    Call<JsonObject> createAppointment(@Body BookAppointmentRequest bookAppointmentRequest);

    @GET("getpatient/{id}")
    Call<JsonObject> getPatientProfile(@Path("id") String id);

    @POST("profile")
    Call<JsonObject> updatePatientProfile(@Body PatientProfileRequest patientProfileRequest);

    @POST("createorder")
    Call<JsonObject> createOrder(@Body CreateOrderRequest createOrderRequest);

    @GET("patientappointment/{id}")
    Call<AppointmentListResponse> getScheduledAppointmentListPatientSide(@Path("id") String id);

    @GET("doctorappointment/{id}")
    Call<AppointmentListResponse> getScheduledAppointmentListDoctorSide(@Path("id") String id);

    @GET("appointment-options")
    Call<AppointmentOptionsResponse> getAppointmentOptions();

    @POST("contact")
    Call<JsonObject> contactUs(@Body HashMap jsonObject);

    @GET("chat-token")
    Call<JsonObject> getChatToken(@Query("identity") String identity, @Query("room_name") String room_name);

    @GET("getpharmacy/{id}")
    Call<PharmacyResponse> getPharmacyById(@Path("id") int id);

    @POST("prescription")
    Call<JsonObject> prescription(@Body AddPrescriptionDoctorSide addPrescriptionDoctorSide);

    @POST("login")
    Call<JsonObject> login(@Body HashMap jsonObject);

    @POST("profile")
    Call<JsonObject> doctorProfile(@Body DoctorProfile doctorProfile);

    @GET("appointment/{id}")
    Call<JsonObject> getAppointmentById(@Path("id") int id);

    @POST("completeappointment/{id}")
    Call<JsonObject> completeAppointment(@Path("id") int id);

    @POST("actionappointment")
    Call<JsonObject> actionappointment(@Body HashMap jsonObject);
}
