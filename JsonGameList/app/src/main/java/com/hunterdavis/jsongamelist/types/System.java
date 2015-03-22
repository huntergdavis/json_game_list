
package com.hunterdavis.jsongamelist.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class System {

    private String name;
    private String url;
    private String releaseDate;
    private Integer quantity;
    private String summary;
    private List<Console> consoles = new ArrayList<Console>();
    private List<Accessory> accessories = new ArrayList<Accessory>();
    private List<Game> games = new ArrayList<Game>();
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
     *     The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * 
     * @param summary
     *     The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 
     * @return
     *     The consoles
     */
    public List<Console> getConsoles() {
        return consoles;
    }

    /**
     * 
     * @param consoles
     *     The consoles
     */
    public void setConsoles(List<Console> consoles) {
        this.consoles = consoles;
    }

    /**
     * 
     * @return
     *     The accessories
     */
    public List<Accessory> getAccessories() {
        return accessories;
    }

    /**
     * 
     * @param accessories
     *     The accessories
     */
    public void setAccessories(List<Accessory> accessories) {
        this.accessories = accessories;
    }

    /**
     * 
     * @return
     *     The games
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     * 
     * @param games
     *     The games
     */
    public void setGames(List<Game> games) {
        this.games = games;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
