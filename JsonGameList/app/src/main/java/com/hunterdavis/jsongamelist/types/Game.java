
package com.hunterdavis.jsongamelist.types;

import java.util.HashMap;
import java.util.Map;

public class Game {

    private String name;
    private String revision;
    private String url;
    private String releaseDate;
    private String condition;
    private Integer quantity;
    private String description;
    private Boolean digitalDistribution;
    private String expirationDate;
    private SystemRequirements systemRequirements;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The revision
     */
    public String getRevision() {
        return revision;
    }

    /**
     * 
     * @param revision
     *     The revision
     */
    public void setRevision(String revision) {
        this.revision = revision;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The releaseDate
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * 
     * @param releaseDate
     *     The releaseDate
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * 
     * @return
     *     The condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * 
     * @param condition
     *     The condition
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * 
     * @return
     *     The quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * 
     * @param quantity
     *     The quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
     *     The digitalDistribution
     */
    public Boolean getDigitalDistribution() {
        return digitalDistribution;
    }

    /**
     * 
     * @param digitalDistribution
     *     The digitalDistribution
     */
    public void setDigitalDistribution(Boolean digitalDistribution) {
        this.digitalDistribution = digitalDistribution;
    }

    /**
     * 
     * @return
     *     The expirationDate
     */
    public String getExpirationDate() {
        return expirationDate;
    }

    /**
     * 
     * @param expirationDate
     *     The expirationDate
     */
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * 
     * @return
     *     The systemRequirements
     */
    public SystemRequirements getSystemRequirements() {
        return systemRequirements;
    }

    /**
     * 
     * @param systemRequirements
     *     The systemRequirements
     */
    public void setSystemRequirements(SystemRequirements systemRequirements) {
        this.systemRequirements = systemRequirements;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
