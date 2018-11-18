package com.mdlink.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PharmacyResponse {
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose OptionDetails pharmacyResponseDetail;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OptionDetails getPharmacyResponseDetail() {
        return pharmacyResponseDetail;
    }

    public void setPharmacyResponseDetail(OptionDetails pharmacyResponseDetail) {
        this.pharmacyResponseDetail = pharmacyResponseDetail;
    }
}
