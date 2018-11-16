package com.mdlink.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentListResponseDetails {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_time")
    @Expose
    private Integer firstTime;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("who_patient")
    @Expose
    private String whoPatient;
    @SerializedName("visit_purpose")
    @Expose
    private String visitPurpose;
    @SerializedName("feeling_when")
    @Expose
    private String feelingWhen;
    @SerializedName("medication_length")
    @Expose
    private String medicationLength;
    @SerializedName("medication")
    @Expose
    private String medication;
    @SerializedName("allergy")
    @Expose
    private String allergy;
    @SerializedName("general_symptoms")
    @Expose
    private String generalSymptoms;
    @SerializedName("chest")
    @Expose
    private String chest;
    @SerializedName("digestive_tract")
    @Expose
    private String digestiveTract;
    @SerializedName("skin")
    @Expose
    private String skin;
    @SerializedName("head_neck")
    @Expose
    private String headNeck;
    @SerializedName("pelvis")
    @Expose
    private String pelvis;
    @SerializedName("muscles_joints")
    @Expose
    private String musclesJoints;
    @SerializedName("medical_conditions")
    @Expose
    private String medicalConditions;
    @SerializedName("pharmacy")
    @Expose
    private String pharmacy;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("scheduled_date")
    @Expose
    private String scheduledDate;
    @SerializedName("scheduled_time")
    @Expose
    private String scheduledTime;
    @SerializedName("preferred_doctor")
    @Expose
    private String preferredDoctor;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("status_id")
    @Expose
    private Integer statusId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("is_payed")
    @Expose
    private Integer isPayed;
    @SerializedName("is_completed")
    @Expose
    private Integer isCompleted;
    @SerializedName("coupon_code")
    @Expose
    private String couponCode;
    @SerializedName("sick_note")
    @Expose
    private Integer sickNote;
    @SerializedName("previous_surgeries")
    @Expose
    private String previousSurgeries;
    @SerializedName("previous_hospital")
    @Expose
    private String previousHospital;
    @SerializedName("is_renew")
    @Expose
    private Integer isRenew;
    @SerializedName("minute_left")
    @Expose
    private Integer minuteLeft;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("doc_name")
    @Expose
    private String docName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Integer firstTime) {
        this.firstTime = firstTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getWhoPatient() {
        return whoPatient;
    }

    public void setWhoPatient(String whoPatient) {
        this.whoPatient = whoPatient;
    }

    public String getVisitPurpose() {
        return visitPurpose;
    }

    public void setVisitPurpose(String visitPurpose) {
        this.visitPurpose = visitPurpose;
    }

    public String getFeelingWhen() {
        return feelingWhen;
    }

    public void setFeelingWhen(String feelingWhen) {
        this.feelingWhen = feelingWhen;
    }

    public String getMedicationLength() {
        return medicationLength;
    }

    public void setMedicationLength(String medicationLength) {
        this.medicationLength = medicationLength;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getGeneralSymptoms() {
        return generalSymptoms;
    }

    public void setGeneralSymptoms(String generalSymptoms) {
        this.generalSymptoms = generalSymptoms;
    }

    public String getChest() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    public String getDigestiveTract() {
        return digestiveTract;
    }

    public void setDigestiveTract(String digestiveTract) {
        this.digestiveTract = digestiveTract;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public String getHeadNeck() {
        return headNeck;
    }

    public void setHeadNeck(String headNeck) {
        this.headNeck = headNeck;
    }

    public String getPelvis() {
        return pelvis;
    }

    public void setPelvis(String pelvis) {
        this.pelvis = pelvis;
    }

    public String getMusclesJoints() {
        return musclesJoints;
    }

    public void setMusclesJoints(String musclesJoints) {
        this.musclesJoints = musclesJoints;
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

    public String getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
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

    public Integer getIsPayed() {
        return isPayed;
    }

    public void setIsPayed(Integer isPayed) {
        this.isPayed = isPayed;
    }

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Integer getSickNote() {
        return sickNote;
    }

    public void setSickNote(Integer sickNote) {
        this.sickNote = sickNote;
    }

    public String getPreviousSurgeries() {
        return previousSurgeries;
    }

    public void setPreviousSurgeries(String previousSurgeries) {
        this.previousSurgeries = previousSurgeries;
    }

    public String getPreviousHospital() {
        return previousHospital;
    }

    public void setPreviousHospital(String previousHospital) {
        this.previousHospital = previousHospital;
    }

    public Integer getIsRenew() {
        return isRenew;
    }

    public void setIsRenew(Integer isRenew) {
        this.isRenew = isRenew;
    }

    public Integer getMinuteLeft() {
        return minuteLeft;
    }

    public void setMinuteLeft(Integer minuteLeft) {
        this.minuteLeft = minuteLeft;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

}
