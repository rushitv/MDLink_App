package com.mdlinkhealth.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddPrescriptionDoctorSide {
    @SerializedName("prescription_date")
    @Expose
    private String prescriptionDate;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("diagnosis")
    @Expose
    private String diagnosis;
    @SerializedName("pharmacy_name")
    @Expose
    private String pharmacyName;
    @SerializedName("pharmacy_fax_number")
    @Expose
    private String pharmacyFaxNumber;
    @SerializedName("lab_name")
    @Expose
    private String labName;
    @SerializedName("radiology_email")
    @Expose
    private String radiologyEmail;
    @SerializedName("radiology_type")
    @Expose
    private String radiologyType;
    @SerializedName("radiology_request")
    @Expose
    private String radiologyRequest;
    @SerializedName("patient_id")
    @Expose
    private String patientId;
    @SerializedName("appointment_id")
    @Expose
    private String appointmentId;
    @SerializedName("patient_name")
    @Expose
    private String patientName;
    @SerializedName("patient_email")
    @Expose
    private String patientEmail;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("patient_address")
    @Expose
    private String patientAddress;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("test")
    @Expose
    private String test;
    @SerializedName("lab_request_note")
    @Expose
    private String labRequestNote;
    @SerializedName("prescription")
    @Expose
    private String prescription;
    @SerializedName("return_date")
    @Expose
    private String returnDate;
    @SerializedName("doctor_id")
    @Expose
    private String doctorId;
    @SerializedName("doctor_name")
    @Expose
    private String doctorName;
    @SerializedName("doctor_email")
    @Expose
    private String doctorEmail;
    @SerializedName("doc_phone_no")
    @Expose
    private String docPhoneNo;

    public String getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(String prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPharmacyFaxNumber() {
        return pharmacyFaxNumber;
    }

    public void setPharmacyFaxNumber(String pharmacyFaxNumber) {
        this.pharmacyFaxNumber = pharmacyFaxNumber;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getRadiologyEmail() {
        return radiologyEmail;
    }

    public void setRadiologyEmail(String radiologyEmail) {
        this.radiologyEmail = radiologyEmail;
    }

    public String getRadiologyType() {
        return radiologyType;
    }

    public void setRadiologyType(String radiologyType) {
        this.radiologyType = radiologyType;
    }

    public String getRadiologyRequest() {
        return radiologyRequest;
    }

    public void setRadiologyRequest(String radiologyRequest) {
        this.radiologyRequest = radiologyRequest;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getLabRequestNote() {
        return labRequestNote;
    }

    public void setLabRequestNote(String labRequestNote) {
        this.labRequestNote = labRequestNote;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getDocPhoneNo() {
        return docPhoneNo;
    }

    public void setDocPhoneNo(String docPhoneNo) {
        this.docPhoneNo = docPhoneNo;
    }
}
