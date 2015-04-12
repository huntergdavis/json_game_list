package com.hunterdavis.jsongamelistmanager.types;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class SystemRequirements {

    private String description;
    private SystemInfo systemInfo = new SystemInfo();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @Override
    public String toString() {
        if (TextUtils.isEmpty(description)) {
            return "";
        }

        return "SystemRequirements{" + '\n' +
                description + '\n' +
                ", systemInfo=" + systemInfo.toString() +
                '}';
    }

}
