package com.hunterdavis.jsongamelistmanager.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonGameList {

    public List<System> systems = new ArrayList<>();
    Basics basics;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public int getSystemsCount() {
        if (systems != null) {
            return systems.size();
        }
        return 0;
    }


    public String getSystemName(int systemOffset) {

        if (systems == null) {
            return "";
        }

        if (systems.size() <= systemOffset) {
            return "";
        }

        return systems.get(systemOffset).getName();
    }


    /**
     * @return The basics
     */
    public Basics getBasics() {
        return basics;
    }

    /**
     * @param basics The basics
     */
    public void setBasics(Basics basics) {
        this.basics = basics;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
