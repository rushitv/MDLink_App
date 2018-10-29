package com.mdlink.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookAppointmentRequest {

    @SerializedName("scheduled_date")
    @Expose
    private String scheduledDate;
    @SerializedName("sick_note")
    @Expose
    private String sickNote;
    @SerializedName("is_renew")
    @Expose
    private String isRenew;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("visit_purpose")
    @Expose
    private String visitPurpose;
    @SerializedName("previous_hospital")
    @Expose
    private String previousHospital;
    @SerializedName("allergy")
    @Expose
    private String allergy;
    @SerializedName("medical_conditions")
    @Expose
    private String medicalConditions;
    @SerializedName("pharmacy")
    @Expose
    private String pharmacy;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("scheduled_time")
    @Expose
    private String scheduledTime;
    @SerializedName("preferred_doctor")
    @Expose
    private String preferredDoctor;
    @SerializedName("coupon_code")
    @Expose
    private String couponCode;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public String getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getIsRenew() {
        return isRenew;
    }

    public void setIsRenew(String isRenew) {
        this.isRenew = isRenew;
    }

    public String getSickNote() {
        return sickNote;
    }

    public void setSickNote(String sickNote) {
        this.sickNote = sickNote;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getVisitPurpose() {
        return visitPurpose;
    }

    public void setVisitPurpose(String visitPurpose) {
        this.visitPurpose = visitPurpose;
    }

    public String getPreviousHospital() {
        return previousHospital;
    }

    public void setPreviousHospital(String previousHospital) {
        this.previousHospital = previousHospital;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getMedicalConditions() {
        return medicalConditions;
    }

    public void setMedicalConditions(String medicalConditions) {
        this.medicalConditions = medicalConditions;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getPreferredDoctor() {
        return preferredDoctor;
    }

    public void setPreferredDoctor(String preferredDoctor) {
        this.preferredDoctor = preferredDoctor;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}