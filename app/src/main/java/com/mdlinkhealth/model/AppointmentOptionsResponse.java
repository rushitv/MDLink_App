package com.mdlinkhealth.model;

import com.google.gson.annotations.SerializedName;

public class AppointmentOptionsResponse {
    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private AppointmentOptions appointmentOptions;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public AppointmentOptions getAppointmentOptions() {
        return appointmentOptions;
    }

    public void setAppointmentOptions(AppointmentOptions appointmentOptions) {
        this.appointmentOptions = appointmentOptions;
    }
}
