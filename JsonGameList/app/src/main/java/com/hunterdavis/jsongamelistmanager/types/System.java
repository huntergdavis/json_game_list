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

    // update for compatibility with steam gamelist data
    public String steamID64;
    public String steamID;

    public void alphabetizeGameList() {
        Collections.sort(games, new Comparator<Game>() {
            @Override
            public int compare(Game g1, Game g2) {
                return g1.getName().toLowerCase().compareTo(g2.getName().toLowerCase());
            }

        });
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
