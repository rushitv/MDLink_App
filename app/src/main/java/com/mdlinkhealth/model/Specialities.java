package com.mdlinkhealth.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Specialities {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("text_price")
    @Expose
    private int textPrice;
    @SerializedName("audio_price")
    @Expose
    private int audioPrice;
    @SerializedName("video_price")
    @Expose
    private int videoPrice;
    @SerializedName("sort_order")
    @Expose
    private int sortOrder;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTextPrice() {
        return textPrice;
    }

    public void setTextPrice(int textPrice) {
        this.textPrice = textPrice;
    }

    public int getAudioPrice() {
        return audioPrice;
    }

    public void setAudioPrice(int audioPrice) {
        this.audioPrice = audioPrice;
    }

    public int getVideoPrice() {
        return videoPrice;
    }

    public void setVideoPrice(int videoPrice) {
        this.videoPrice = videoPrice;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
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

}