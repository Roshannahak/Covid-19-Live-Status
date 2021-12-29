
package com.colive.covid19.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CovidDataIndia {

    @SerializedName("statewise")
    @Expose
    private List<Statewise> statewise = null;
    @SerializedName("tested")
    @Expose
    private List<Tested> tested = null;

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
