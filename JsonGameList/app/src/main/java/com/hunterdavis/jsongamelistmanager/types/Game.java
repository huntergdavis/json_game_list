package com.hunterdavis.jsongamelistmanager.types;

import java.util.ArrayList;
import java.util.Map;

public class Game extends SystemItemWithMetadata {


    private Boolean digitalDistribution;
    private String expirationDate;
    private SystemRequirements systemRequirements = new SystemRequirements();
    public ArrayList<String> videos = new ArrayList<String>();

    // update for data from steam DB
    public String logo;
    public String hoursLastTwoWeeeks;
    public String hoursOnRecord;
    public String statsLink;
    public String globalStatsLink;
    public String appID;


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
