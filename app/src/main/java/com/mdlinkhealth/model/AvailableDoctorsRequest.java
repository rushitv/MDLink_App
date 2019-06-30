package com.mdlinkhealth.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailableDoctorsRequest {
    @SerializedName("scheduled_date")
    @Expose
    private String ScheduledDate;
    @SerializedName("scheduled_time")
    @Expose
    private String ScheduledTime;
    @SerializedName("specialities_id")
    @Expose
    private int SpecialitiesId;

    public String getScheduledDate() {
        return ScheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        ScheduledDate = scheduledDate;
    }

    public String getScheduledTime() {
        return ScheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        ScheduledTime = scheduledTime;
    }

    public int getSpecialitiesId() {
        return SpecialitiesId;
    }

    public void setSpecialitiesId(int specialitiesId) {
        SpecialitiesId = specialitiesId;
    }
}
