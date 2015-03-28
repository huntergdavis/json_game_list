package com.hunterdavis.jsongamelist.types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class System {

    private String name = "";
    private String url = "";
    private String releaseDate = "";
    private String summary = "";
    private List<Console> consoles = new ArrayList<Console>();
    private List<Accessory> accessories = new ArrayList<Accessory>();
    private List<Game> games = new ArrayList<Game>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public int getListItemCount() {

        // 1 list item just for showing the console information
        int listItemCount = 1;

        listItemCount += consoles.size();
        listItemCount += accessories.size();
        listItemCount += games.size();

        return listItemCount;
    }

    public void alphebetizeGamesList() {
        Collections.sort(games, new Comparator<Game>() {
            @Override
            public int compare(Game g1, Game g2) {
                return g1.getName().compareTo(g2.getName());
            }

        });
    }

    public String getSystemListItemName(int itemOffset) {
        if (itemOffset == 0) {
            // self case
            return getName() + " (System)";
        } else if ((itemOffset < consoles.size() + 1) && (consoles.size() > 0)) {
            // consoles case
            return consoles.get(itemOffset - 1).getName();
        } else if ((itemOffset < (consoles.size() + accessories.size() + 1)) && (accessories.size() > 0)) {
            // accessories case
            return accessories.get(itemOffset - 1 - consoles.size()).getName();
        } else {
            // games case
            return games.get(itemOffset - 1 - consoles.size() - accessories.size()).getName();
        }
    }

    public String getSystemListItemRevision(int itemOffset) {
        if (itemOffset == 0) {
            // self case
            return "Contains " + consoles.size() + " consoles, " + accessories.size() + " accessories, " + " and " + games.size() + " games.";
        } else if ((itemOffset < consoles.size() + 1) && (consoles.size() > 0)) {
            // consoles case
            return consoles.get(itemOffset - 1).getRevision();
        } else if ((itemOffset < (consoles.size() + accessories.size() + 1)) && (accessories.size() > 0)) {
            // accessories case
            return accessories.get(itemOffset - 1 - consoles.size()).getRevision();
        } else {
            // games case
            return games.get(itemOffset - 1 - consoles.size() - accessories.size()).getRevision();
        }
    }

    public String getSystemListItemUrl(int itemOffset) {
        if (itemOffset == 0) {
            // self case
            return getUrl();
        } else if ((itemOffset < consoles.size() + 1) && (consoles.size() > 0)) {
            // consoles case
            return consoles.get(itemOffset - 1).getUrl();
        } else if ((itemOffset < (consoles.size() + accessories.size() + 1)) && (accessories.size() > 0)) {
            // accessories case
            return accessories.get(itemOffset - 1 - consoles.size()).getUrl();
        } else {
            // games case
            return games.get(itemOffset - 1 - consoles.size() - accessories.size()).getUrl();
        }
    }

    public String getSystemListItemReleaseDate(int itemOffset) {
        if (itemOffset == 0) {
            // self case
            return getReleaseDate();
        } else if ((itemOffset < consoles.size() + 1) && (consoles.size() > 0)) {
            // consoles case
            return consoles.get(itemOffset - 1).getReleaseDate();
        } else if ((itemOffset < (consoles.size() + accessories.size() + 1)) && (accessories.size() > 0)) {
            // accessories case
            return accessories.get(itemOffset - 1 - consoles.size()).getReleaseDate();
        } else {
            // games case
            return games.get(itemOffset - 1 - consoles.size() - accessories.size()).getReleaseDate();
        }
    }

    public String getSystemListItemCondition(int itemOffset) {
        if (itemOffset == 0) {
            // self case
            return "";
        } else if ((itemOffset < consoles.size() + 1) && (consoles.size() > 0)) {
            // consoles case
            return consoles.get(itemOffset - 1).getCondition();
        } else if ((itemOffset < (consoles.size() + accessories.size() + 1)) && (accessories.size() > 0)) {
            // accessories case
            return accessories.get(itemOffset - 1 - consoles.size()).getCondition();
        } else {
            // games case
            return games.get(itemOffset - 1 - consoles.size() - accessories.size()).getCondition();
        }
    }

    public String getSystemListItemQuantity(int itemOffset) {
        if (itemOffset == 0) {
            // self case
            return "";
        } else if ((itemOffset < consoles.size() + 1) && (consoles.size() > 0)) {
            if (consoles.get(itemOffset - 1).getQuantity() == null) {
                return "";
            }

            // consoles case
            return "(qty: " + consoles.get(itemOffset - 1).getQuantity() + ")";
        } else if ((itemOffset < (consoles.size() + accessories.size() + 1)) && (accessories.size() > 0)) {
            if (accessories.get(itemOffset - 1 - consoles.size()).getQuantity() == null) {
                return "";
            }
            // accessories case
            return "(qty: " + accessories.get(itemOffset - 1 - consoles.size()).getQuantity() + ")";
        } else {
            if (games.get(itemOffset - 1 - consoles.size() - accessories.size()).getQuantity() == null) {
                return "";
            }
            // games case
            return "(qty: " + games.get(itemOffset - 1 - consoles.size() - accessories.size()).getQuantity() + ")";
        }
    }

    public String getSystemListItemDescription(int itemOffset) {
        if (itemOffset == 0) {
            // self case
            return getSummary();
        } else if ((itemOffset < consoles.size() + 1) && (consoles.size() > 0)) {
            // consoles case
            return consoles.get(itemOffset - 1).getDescription();
        } else if ((itemOffset < (consoles.size() + accessories.size() + 1)) && (accessories.size() > 0)) {
            // accessories case
            return accessories.get(itemOffset - 1 - consoles.size()).getDescription();
        } else {
            // games case
            return games.get(itemOffset - 1 - consoles.size() - accessories.size()).getDescription();
        }
    }

    public String getSystemListItemSystemRequirements(int itemOffset) {
        if (itemOffset == 0) {
            // self case
            return "";
        } else if ((itemOffset < consoles.size() + 1) && (consoles.size() > 0)) {
            // consoles case
            return consoles.get(itemOffset - 1).getSystemInfo().toString();
        } else if ((itemOffset < (consoles.size() + accessories.size() + 1)) && (accessories.size() > 0)) {
            // accessories case
            return accessories.get(itemOffset - 1 - consoles.size()).systemRequirements.toString();
        } else {
            // games case
            return games.get(itemOffset - 1 - consoles.size() - accessories.size()).getSystemRequirements().toString();
        }
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
     * @return The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return The consoles
     */
    public List<Console> getConsoles() {
        return consoles;
    }

    /**
     * @param consoles The consoles
     */
    public void setConsoles(List<Console> consoles) {
        this.consoles = consoles;
    }

    /**
     * @return The accessories
     */
    public List<Accessory> getAccessories() {
        return accessories;
    }

    /**
     * @param accessories The accessories
     */
    public void setAccessories(List<Accessory> accessories) {
        this.accessories = accessories;
    }

    /**
     * @return The games
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     * @param games The games
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
