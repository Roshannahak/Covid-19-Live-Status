
package com.colive.covid19.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Statewise {

    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("confirmed")
    @Expose
    private String confirmed;
    @SerializedName("deaths")
    @Expose
    private String deaths;
    @SerializedName("deltaconfirmed")
    @Expose
    private String deltaconfirmed;
    @SerializedName("deltadeaths")
    @Expose
    private String deltadeaths;
    @SerializedName("deltarecovered")
    @Expose
    private String deltarecovered;
    @SerializedName("lastupdatedtime")
    @Expose
    private String lastupdatedtime;
    @SerializedName("migratedother")
    @Expose
    private String migratedother;
    @SerializedName("recovered")
    @Expose
    private String recovered;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("statecode")
    @Expose
    private String statecode;
    @SerializedName("statenotes")
    @Expose
    private String statenotes;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getDeltaconfirmed() {
        return deltaconfirmed;
    }

    public void setDeltaconfirmed(String deltaconfirmed) {
        this.deltaconfirmed = deltaconfirmed;
    }

    public String getDeltadeaths() {
        return deltadeaths;
    }

    public void setDeltadeaths(String deltadeaths) {
        this.deltadeaths = deltadeaths;
    }

    public String getDeltarecovered() {
        return deltarecovered;
    }

    public void setDeltarecovered(String deltarecovered) {
        this.deltarecovered = deltarecovered;
    }

    public String getLastupdatedtime() {
        return lastupdatedtime;
    }

    public void setLastupdatedtime(String lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

    public String getMigratedother() {
        return migratedother;
    }

    public void setMigratedother(String migratedother) {
        this.migratedother = migratedother;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatecode() {
        return statecode;
    }

    public void setStatecode(String statecode) {
        this.statecode = statecode;
    }

    public String getStatenotes() {
        return statenotes;
    }

    public void setStatenotes(String statenotes) {
        this.statenotes = statenotes;
    }

}
