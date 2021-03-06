package com.hunterdavis.jsongamelistmanager.types;

import java.util.ArrayList;
import java.util.Map;

public class Game extends SystemItemWithMetadata {


    private Boolean digitalDistribution;
    private String expirationDate;
    private SystemRequirements systemRequirements = new SystemRequirements();

    // update for data from steam DB
    public String hoursLast2Weeks;
    public String hoursOnRecord;
    public String statsLink;
    public String globalStatsLink;
    public String appID;
    public String systemName;


    /**
     * @return The systemRequirements
     */
    public SystemRequirements getSystemRequirements() {
        return systemRequirements;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
