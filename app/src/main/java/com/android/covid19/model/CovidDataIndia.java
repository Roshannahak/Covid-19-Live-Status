
package com.android.covid19.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CovidDataIndia {

    @SerializedName("cases_time_series")
    @Expose
    private List<CasesTimeSeries> casesTimeSeries = null;
    @SerializedName("statewise")
    @Expose
    private List<Statewise> statewise = null;
    @SerializedName("tested")
    @Expose
    private List<Tested> tested = null;

    public List<CasesTimeSeries> getCasesTimeSeries() {
        return casesTimeSeries;
    }

    public void setCasesTimeSeries(List<CasesTimeSeries> casesTimeSeries) {
        this.casesTimeSeries = casesTimeSeries;
    }

    public List<Statewise> getStatewise() {
        return statewise;
    }

    public void setStatewise(List<Statewise> statewise) {
        this.statewise = statewise;
    }

    public List<Tested> getTested() {
        return tested;
    }

    public void setTested(List<Tested> tested) {
        this.tested = tested;
    }

}
