package com.mdlink.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorProfile {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("qualification")
    @Expose
    private String qualification;
    @SerializedName("speciality")
    @Expose
    private String speciality;
    @SerializedName("avail_morning")
    @Expose
    private String availMorning;
    @SerializedName("avail_evening")
    @Expose
    private String availEvening;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("medical_school")
    @Expose
    private String medicalSchool;
    @SerializedName("medical_council")
    @Expose
    private String medicalCouncil;
    @SerializedName("graduation_year")
    @Expose
    private String graduationYear;
    @SerializedName("registration_body")
    @Expose
    private String registrationBody;
    @SerializedName("registration_number")
    @Expose
    private String registrationNumber;
    @SerializedName("signature")
    @Expose
    private String signature;
    @SerializedName("medical_certificate")
    @Expose
    private String medicalCertificate;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("available_day")
    @Expose
    private String availableDay;
    @SerializedName("avail_morning_to")
    @Expose
    private String availMorningTo;
    @SerializedName("avail_evening_to")
    @Expose
    private String availEveningTo;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("userID")
    @Expose
    private String userID;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("role_id")
    @Expose
    private int roleId;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("birthdate")
    @Expose
    private String birthdate;
    @SerializedName("address")
    @Expose
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getAvailMorning() {
        return availMorning;
    }

    public void setAvailMorning(String availMorning) {
        this.availMorning = availMorning;
    }

    public String getAvailEvening() {
        return availEvening;
    }

    public void setAvailEvening(String availEvening) {
        this.availEvening = availEvening;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    public String getRegistrationBody() {
        return registrationBody;
    }

    public void setRegistrationBody(String registrationBody) {
        this.registrationBody = registrationBody;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getMedicalCertificate() {
        return medicalCertificate;
    }

    public void setMedicalCertificate(String medicalCertificate) {
        this.medicalCertificate = medicalCertificate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAvailableDay() {
        return availableDay;
    }

    public void setAvailableDay(String availableDay) {
        this.availableDay = availableDay;
    }

    public String getAvailMorningTo() {
        return availMorningTo;
    }

    public void setAvailMorningTo(String availMorningTo) {
        this.availMorningTo = availMorningTo;
    }

    public String getAvailEveningTo() {
        return availEveningTo;
    }

    public void setAvailEveningTo(String availEveningTo) {
        this.availEveningTo = availEveningTo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}