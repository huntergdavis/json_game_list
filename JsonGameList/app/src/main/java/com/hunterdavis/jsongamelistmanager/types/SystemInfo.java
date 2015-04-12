package com.hunterdavis.jsongamelistmanager.types;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class SystemInfo {

    private String cpu;
    private String cpuSpeed;
    private String cpuRam;
    private String gpu;
    private String gpuRam;
    private String operatingSystem;
    private String operatingSystemVersion;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @Override
    public String toString() {
        return returnIfNotNull(cpu) +
                returnIfNotNull(cpuSpeed) +
                returnIfNotNull(cpuRam) +
                returnIfNotNull(gpu) +
                returnIfNotNull(gpuRam) +
                returnIfNotNull(operatingSystem) +
                returnIfNotNull(operatingSystemVersion);
    }

    public String returnIfNotNull(String name) {
        if (TextUtils.isEmpty(name)) {
            return "";
        } else {
            return name + "\n";
        }
    }

}
