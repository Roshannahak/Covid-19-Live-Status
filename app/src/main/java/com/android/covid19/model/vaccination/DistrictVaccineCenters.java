
package com.android.covid19.model.vaccination;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DistrictVaccineCenters {

    @SerializedName("sessions")
    @Expose
    private List<Session> sessions = null;

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

}
