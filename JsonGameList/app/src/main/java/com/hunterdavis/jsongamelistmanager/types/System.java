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

    public void alphabetizeSystemItemLists() {
        Collections.sort(consoles, getSystemItemComparator());
        Collections.sort(accessories, getSystemItemComparator());
        Collections.sort(games, getSystemItemComparator());
        Collections.sort(movies, getSystemItemComparator());
        Collections.sort(music, getSystemItemComparator());
    }

    public Comparator<SystemItemWithMetadata> getSystemItemComparator() {
        return new Comparator<SystemItemWithMetadata>() {
            @Override
            public int compare(SystemItemWithMetadata g1, SystemItemWithMetadata g2) {
                return g1.getName().toLowerCase().compareTo(g2.getName().toLowerCase());
            }

        };
    }

    public String getRevision() {
        ArrayList<String> listOfItems = new ArrayList<>();

        if(consoles.size() > 0) {
            listOfItems.add(consoles.size() + " consoles");
        }
        if(accessories.size() > 0) {
            listOfItems.add(accessories.size() + " accessories");
        }
        if(games.size() > 0) {
            listOfItems.add(games.size() + " games");
        }
        if(movies.size() > 0) {
            listOfItems.add(movies.size() + " movies");
        }
        if(music.size() > 0) {
            listOfItems.add(music.size() + " music");
        }

        return TextUtils.join(",",listOfItems);

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
