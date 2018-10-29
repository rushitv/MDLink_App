package com.mdlink.api;

import com.google.gson.JsonObject;
import com.mdlink.model.BookAppointmentRequest;
import com.mdlink.model.DoctorPortalRequest;
import com.mdlink.model.DoctorPortalResponse;
import com.mdlink.model.DoctorsListModel;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
            @Part MultipartBody.Part medical_certificate
    );

    @POST("availabledoctors")
    Call<DoctorsListModel> availabledoctors(@Body HashMap jsonObject);

    @POST("appointment")
    Call<JsonObject> createAppointment(@Body BookAppointmentRequest bookAppointmentRequest);
}
