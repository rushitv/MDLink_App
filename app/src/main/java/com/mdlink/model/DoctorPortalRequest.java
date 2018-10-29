package com.mdlink.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorPortalRequest {
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("speciality")
    @Expose
    private String speciality;
    @SerializedName("medical_school")
    @Expose
    private String medicalSchool;
    @SerializedName("medical_council")
    @Expose
    private String medicalCouncil;
    @SerializedName("graduation_year")
    @Expose
    private String graduationYear;
    @SerializedName("registration_number")
    @Expose
    private String registrationNumber;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("confirmpassword")
    @Expose
    private String confirmpassword;
    @SerializedName("available_day")
    @Expose
    private String availableDay;
    @SerializedName("avail_morning")
    @Expose
    private String availMorning;
    @SerializedName("avail_morning_to")
    @Expose
    private String availMorningTo;
    @SerializedName("avail_evening")
    @Expose
    private String availEvening;
    @SerializedName("avail_evening_to")
    @Expose
    private String availEveningTo;
    @SerializedName("terms_and_cond")
    @Expose
    private String termsAndCond;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("role_id")
    @Expose
    private Integer roleId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getMedicalSchool() {
        return medicalSchool;
    }

    public void setMedicalSchool(String medicalSchool) {
        this.medicalSchool = medicalSchool;
    }

    public String getMedicalCouncil() {
        return medicalCouncil;
    }

    public void setMedicalCouncil(String medicalCouncil) {
        this.medicalCouncil = medicalCouncil;
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public String getAvailableDay() {
        return availableDay;
    }

    public void setAvailableDay(String availableDay) {
        this.availableDay = availableDay;
    }

    public String getAvailMorning() {
        return availMorning;
    }

    public void setAvailMorning(String availMorning) {
        this.availMorning = availMorning;
    }

    public String getAvailMorningTo() {
        return availMorningTo;
    }

    public void setAvailMorningTo(String availMorningTo) {
        this.availMorningTo = availMorningTo;
    }

    public String getAvailEvening() {
        return availEvening;
    }

    public void setAvailEvening(String availEvening) {
        this.availEvening = availEvening;
    }

    public String getAvailEveningTo() {
        return availEveningTo;
    }

    public void setAvailEveningTo(String availEveningTo) {
        this.availEveningTo = availEveningTo;
    }

    public String getTermsAndCond() {
        return termsAndCond;
    }

    public void setTermsAndCond(String termsAndCond) {
        this.termsAndCond = termsAndCond;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
