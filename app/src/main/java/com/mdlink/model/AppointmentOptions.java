package com.mdlink.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppointmentOptions {
    @SerializedName("labs")
    @Expose
    private List<OptionDetails> labs = null;
    @SerializedName("pharmacys")
    @Expose
    private List<OptionDetails> pharmacys = null;
    @SerializedName("radiologys")
    @Expose
    private List<OptionDetails> radiologys = null;

    public List<OptionDetails> getLabs() {
        return labs;
    }

    public void setLabs(List<OptionDetails> labs) {
        this.labs = labs;
    }

    public List<OptionDetails> getPharmacys() {
        return pharmacys;
    }

    public void setPharmacys(List<OptionDetails> pharmacys) {
        this.pharmacys = pharmacys;
    }

    public List<OptionDetails> getRadiologys() {
        return radiologys;
    }

    public void setRadiologys(List<OptionDetails> radiologys) {
        this.radiologys = radiologys;
    }

}

