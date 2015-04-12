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

}
