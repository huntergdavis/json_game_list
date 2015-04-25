package com.hunterdavis.jsongamelistmanager.types;

import android.content.Context;

import com.hunterdavis.jsongamelistmanager.JsonGameListParser;
import com.hunterdavis.jsongamelistmanager.R;

import java.util.HashMap;
import java.util.Map;

public abstract class SystemItemWithMetadata {
    public String name;
    public String revision;
    public String url;
    public String releaseDate;
    public String condition;
    public Integer quantity;
    public String description;
    public String gameRegion;
    public String language;
    public Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }
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
        if(null == quantity) {
            return 1;
        }
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

    public String getDescriptiveQuantity(Context context) {
        if(null == getQuantity()) {
            return "";
        }else if (1 == getQuantity()) {
            return "";
        }else if (getQuantity() < 1) {
            return " (" + context.getString(R.string.wishlist) + ")";
        }

        return " (qty: " + getQuantity() + ")";
    }

    public String getStringIfProperty(String property, String errorString) {
        if(getAdditionalProperties().containsKey(property)) {
            return errorString;
        }else {
            return "";
        }
    }

}