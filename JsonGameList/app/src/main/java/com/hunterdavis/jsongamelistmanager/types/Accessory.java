package com.hunterdavis.jsongamelistmanager.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Accessory extends ObjectWithAdditionalProperty {

    public String revision;
    public String url;
    public String releaseDate;
    public String condition;
    public Integer quantity;
    public String description;
    public SystemRequirements systemRequirements = new SystemRequirements();
    public ArrayList<String> videos = new ArrayList<String>();
    public String logo;


    /**
     * @return The revision
     */
    public String getRevision() {
        return revision;
    }

    /**
     * @param revision The revision
     */
    public void setRevision(String revision) {
        this.revision = revision;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The releaseDate
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate The releaseDate
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return The condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @param condition The condition
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * @return The quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * @param quantity The quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
