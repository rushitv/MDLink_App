package com.mdlink.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateOrderRequest {

    @SerializedName("app_id")
    @Expose
    private Integer appId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("transaction_status")
    @Expose
    private String transactionStatus;
    @SerializedName("transaction_response")
    @Expose
    private String transactionResponse;
    @SerializedName("coupon_code")
    @Expose
    private String couponCode;

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionResponse() {
        return transactionResponse;
    }

    public void setTransactionResponse(String transactionResponse) {
        this.transactionResponse = transactionResponse;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

}