package com.hunterdavis.jsongamelistmanager.types;

import java.util.HashMap;
import java.util.Map;

public abstract class ObjectWithAdditionalProperty {
    protected Map<String, Object> additionalProperties = new HashMap<String, Object>();


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}