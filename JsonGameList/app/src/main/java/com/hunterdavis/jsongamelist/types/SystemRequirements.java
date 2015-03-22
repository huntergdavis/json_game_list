
package com.hunterdavis.jsongamelist.types;

import java.util.HashMap;
import java.util.Map;

public class SystemRequirements {

    private String description;
    private SystemInfo systemInfo;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @Override
    public String toString() {
        return "SystemRequirements{" +'\n'+
                description + '\'' +
                ", systemInfo=" + systemInfo.toString() +
                '}';
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The systemInfo
     */
    public SystemInfo getSystemInfo() {
        return systemInfo;
    }

    /**
     * 
     * @param systemInfo
     *     The systemInfo
     */
    public void setSystemInfo(SystemInfo systemInfo) {
        this.systemInfo = systemInfo;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
