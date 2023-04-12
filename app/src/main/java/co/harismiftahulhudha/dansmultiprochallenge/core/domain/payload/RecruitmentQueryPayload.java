package co.harismiftahulhudha.dansmultiprochallenge.core.domain.payload;

import java.util.HashMap;

public class RecruitmentQueryPayload {
    private final String description;
    private final String location;
    private final String fullTime;

    public RecruitmentQueryPayload(String description, String location, String fullTime) {
        this.description = description;
        this.location = location;
        this.fullTime = fullTime;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String isFullTime() {
        return fullTime;
    }

    public HashMap<String, String> toHashMap() {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        if (this.description != null && !this.description.equals("")) {
            hashMap.put("description", this.description);
        }
        if (this.location != null && !this.location.equals("")) {
            hashMap.put("location", this.location);
        }
        if (this.fullTime != null && !this.fullTime.equals("")) {
            hashMap.put("full_time", this.fullTime);
        }

        return hashMap;
    }
}
