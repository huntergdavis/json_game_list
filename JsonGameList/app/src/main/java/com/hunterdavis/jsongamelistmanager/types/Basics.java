package com.hunterdavis.jsongamelistmanager.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basics {

    private String name;
    private String label;
    private String picture;
    private String email;
    private String phone;
    private String website;
    private String summary;
    private Location location;
    private List<System> systems = new ArrayList<System>();
    public ArrayList<String> videos = new ArrayList<String>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @return The label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return The picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @return The website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @return The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @return The location
     */
    public Location getLocation() {
        return location;
    }

}
