package com.hunterdavis.jsongamelistmanager.types;

import java.util.ArrayList;

public class Console extends SystemItemWithMetadata {

    private SystemInfo systemInfo;
    public ArrayList<String> videos = new ArrayList<String>();
    public String logo;

    /**
     * @return The systemInfo
     */
    public SystemInfo getSystemInfo() {
        return systemInfo;
    }


}
