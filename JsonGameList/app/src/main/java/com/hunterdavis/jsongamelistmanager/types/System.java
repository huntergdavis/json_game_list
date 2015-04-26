package com.hunterdavis.jsongamelistmanager.types;

import android.content.Context;
import android.text.TextUtils;

import com.hunterdavis.jsongamelistmanager.JsonGameListParser;
import com.hunterdavis.jsongamelistmanager.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class System extends SystemItemWithMetadata {

    private String summary = "";
    public List<Console> consoles = new ArrayList<Console>();
    public List<Accessory> accessories = new ArrayList<Accessory>();
    public List<Game> games = new ArrayList<Game>();
    public List<SystemItemWithMetadata> movies = new ArrayList<>();
    public List<SystemItemWithMetadata> music = new ArrayList<>();
    public ArrayList<String> videos = new ArrayList<String>();

    // update for compatibility with steam gamelist data
    public String steamID64;
    public String steamID;
    public String logo;

    public void alphabetizeGameList() {
        Collections.sort(games, new Comparator<Game>() {
            @Override
            public int compare(Game g1, Game g2) {
                return g1.getName().toLowerCase().compareTo(g2.getName().toLowerCase());
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

    public String getSystemListYoutube(int itemOffset) {
        if (itemOffset == 0) {
            // self case
            if(videos.size() > 0) {
                return videos.get(0);
            }
        } else if ((itemOffset < consoles.size() + 1) && (consoles.size() > 0)) {
            // consoles case
            if(consoles.get(itemOffset - 1).videos.size() > 0) {
                return consoles.get(itemOffset - 1).videos.get(0);
            }
        } else if ((itemOffset < (consoles.size() + accessories.size() + 1)) && (accessories.size() > 0)) {
            // accessories case
            if(accessories.get(itemOffset - 1 - consoles.size()).videos.size() > 0) {
                return accessories.get(itemOffset - 1 - consoles.size()).videos.get(0);
            }
        } else {
            // games case
            if(games.get(itemOffset - 1 - consoles.size() - accessories.size()).videos.size() > 0) {
                return games.get(itemOffset - 1 - consoles.size() - accessories.size()).videos.get(0);
            }
        }
        return "";
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



    public String getSystemListItemDuplicates(int itemOffset, String errorString) {
        if (itemOffset == 0) {
            // self case
            return "";
        } else if ((itemOffset < consoles.size() + 1) && (consoles.size() > 0)) {
            // consoles case
            if(consoles.get(itemOffset - 1).getAdditionalProperties().containsKey(JsonGameListParser.PROPERTY_DUPLICATE)) {
                return errorString;
            }
        } else if ((itemOffset < (consoles.size() + accessories.size() + 1)) && (accessories.size() > 0)) {
            // accessories case
            if(accessories.get(itemOffset - 1 - consoles.size()).getAdditionalProperties().containsKey(JsonGameListParser.PROPERTY_DUPLICATE)) {
                return errorString;
            }
        } else {
            // games case
            if (games.get(itemOffset - 1 - consoles.size() - accessories.size()).getAdditionalProperties().containsKey(JsonGameListParser.PROPERTY_DUPLICATE)) {
                return errorString;
            }
        }

        return "";
    }


    public String getSystemListItemLogoImage(int itemOffset) {
        if (itemOffset == 0) {
            // self case
            return logo;
        } else if ((itemOffset < consoles.size() + 1) && (consoles.size() > 0)) {
            // consoles case
            return consoles.get(itemOffset - 1).logo;
        } else if ((itemOffset < (consoles.size() + accessories.size() + 1)) && (accessories.size() > 0)) {
            // accessories case
            return accessories.get(itemOffset - 1 - consoles.size()).logo;
        } else {
            // games case
            return games.get(itemOffset - 1 - consoles.size() - accessories.size()).logo;
        }
    }

    public String getSystemListItemStatsLink(int itemOffset) {
        if (itemOffset == 0) {
            // self case
            return "";
        } else if ((itemOffset < consoles.size() + 1) && (consoles.size() > 0)) {
            // consoles case
            return "";
        } else if ((itemOffset < (consoles.size() + accessories.size() + 1)) && (accessories.size() > 0)) {
            // accessories case
            return "";
        } else {
            // games case
            return games.get(itemOffset - 1 - consoles.size() - accessories.size()).statsLink;
        }
    }

    public String getSystemListItemGlobalStatsLink(int itemOffset) {
        if (itemOffset == 0) {
            // self case
            return "";
        } else if ((itemOffset < consoles.size() + 1) && (consoles.size() > 0)) {
            // consoles case
            return "";
        } else if ((itemOffset < (consoles.size() + accessories.size() + 1)) && (accessories.size() > 0)) {
            // accessories case
            return "";
        } else {
            // games case
            return games.get(itemOffset - 1 - consoles.size() - accessories.size()).statsLink;
        }
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

}
