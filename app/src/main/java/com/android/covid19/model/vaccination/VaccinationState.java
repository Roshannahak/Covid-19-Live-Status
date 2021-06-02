
package com.android.covid19.model.vaccination;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VaccinationState {

    @SerializedName("states")
    @Expose
    private List<State> states = null;
    @SerializedName("ttl")
    @Expose
    private Integer ttl;

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public Integer getTtl() {
        return ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

}
