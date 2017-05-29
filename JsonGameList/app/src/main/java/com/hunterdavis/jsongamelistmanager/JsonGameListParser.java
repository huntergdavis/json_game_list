package com.hunterdavis.jsongamelistmanager;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.hunterdavis.jsongamelistmanager.types.*;
import com.hunterdavis.jsongamelistmanager.types.System;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hunter on 3/22/15.
 */
public class JsonGameListParser {

    public static final String PROPERTY_DUPLICATE = "duplicate";
    public static final String PROPERTY_DUPLICATE_OTHER_CONSOLE = "duplicate_other_console";
    public static final String PROPERTY_WISHLIST = "wishlist";


    /**
     * parse Hunter's current resume from the assets folder
     *
     * @param context
     * @return
     */
    public static JsonGameList parseHuntersGameList(Context context) throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream;
        inputStream = assetManager.open("gamelist.json");
        return parseInputStreamForJsonResume(inputStream);
    }

    /**
     * parse an inputstream for a Resume object
     *
     * @param input
     * @return
     */
    public static JsonGameList parseInputStreamForJsonResume(InputStream input) throws UnsupportedEncodingException {

        JsonReader reader = new JsonReader(new InputStreamReader(input, "UTF-8"));
        Gson gson = new Gson();
        JsonGameList gameList = gson.fromJson(reader, JsonGameList.class);

        return sanitizeMarkDuplicatesAndWishListInGameList(gameList);
    }


    public static Comparator<System> getSystemListComparator() {
        return new Comparator<System>() {
            @Override
            public int compare(System g1, System g2) {
                return g1.getName().toLowerCase().compareTo(g2.getName().toLowerCase());
            }
        };
    }

    private static JsonGameList sanitizeMarkDuplicatesAndWishListInGameList(JsonGameList gameList) {
        Set<String> systemNames = new HashSet<String>();
        Set<String> crossGameNames = new HashSet<String>();
        Set<String> crossGameNamesDupes = new HashSet<String>();
        Set<String> systemNamesDupes = new HashSet<String>();

        //sanitize - alphebatize the list of systems
        Collections.sort(gameList.systems, getSystemListComparator());


        // first loop, mark all dupes, mark wish list
        for (System system : gameList.systems) {
            Set<String> consoleNamesDupes = new HashSet<String>();
            Set<String> accessoryNamesDupes = new HashSet<String>();
            Set<String> gameNamesDupes = new HashSet<String>();

            setDuplicateAndAddToDupeListIfCollision(system, systemNames, systemNamesDupes);

            Set<String> consoleNames = new HashSet<String>();
            for (Console console : system.consoles) {
                setDuplicateAndAddToDupeListIfCollision(console, consoleNames, consoleNamesDupes);
            }

            Set<String> accessoryNames = new HashSet<String>();
            for (Accessory acc : system.accessories) {
                setDuplicateAndAddToDupeListIfCollision(acc, accessoryNames, accessoryNamesDupes);
            }

            Set<String> gameNames = new HashSet<String>();
            for (Game game : system.games) {
                setDuplicateAndAddToDupeListIfCollision(game, gameNames, gameNamesDupes);
                setCrossDuplicateAndAddToDupeListIfCollision(game, crossGameNames, crossGameNamesDupes);
            }

            // second loop, mark dupes of dupes within same system
            for (Console console : system.consoles) {
                setDupeIfSetContainsRef(console, consoleNamesDupes);
            }

            for (Accessory acc : system.accessories) {
                setDupeIfSetContainsRef(acc, accessoryNamesDupes);
            }

            for (Game game : system.games) {
                setDupeIfSetContainsRef(game, gameNamesDupes);
                setCrossDupeIfCollision(game, crossGameNamesDupes);
            }
        }

        // second loop, mark dupes of dupes for cross refs
        for (System system : gameList.systems) {
            for (Game game : system.games) {
                setCrossDupeIfCollision(game, crossGameNamesDupes);
            }
        }

        return gameList;
    }

    /**
     * if the set contains the name of the item, set cross duplicate
     * @param oWap
     * @param setRef
     */
    public static void setCrossDupeIfCollision(SystemItemWithMetadata oWap, Set<String> setRef) {
        setBooleanTruePropertyIfCollision(oWap, setRef, PROPERTY_DUPLICATE_OTHER_CONSOLE);
    }

    public static void setDupeIfSetContainsRef(SystemItemWithMetadata oWap, Set<String> setRef) {
        setBooleanTruePropertyIfCollision(oWap, setRef, PROPERTY_DUPLICATE);
    }


    public static void setDuplicateAndAddToDupeListIfCollision(SystemItemWithMetadata oWap, Set<String> setRef, Set<String> dupeRef) {
        setBooleanTruePropertyIfEntryCollisionAndAddToOtherSet(oWap, setRef, PROPERTY_DUPLICATE, dupeRef);
    }

    public static void setCrossDuplicateAndAddToDupeListIfCollision(SystemItemWithMetadata oWap, Set<String> setRef, Set<String> dupeRef) {
        setBooleanTruePropertyIfEntryCollisionAndAddToOtherSet(oWap, setRef, PROPERTY_DUPLICATE_OTHER_CONSOLE, dupeRef);
    }


    public static void setBooleanTruePropertyIfCollision(SystemItemWithMetadata item, Set<String> setOfNames, String property) {
        if(setOfNames.contains(item.getName())) {
            item.setAdditionalProperty(property,true);
        }
    }

    public static void setBooleanTruePropertyIfEntryCollisionAndAddToOtherSet(SystemItemWithMetadata item, Set<String> setOfNames, String property, Set<String> dupeSet) {
        if(!setOfNames.add(item.getName())) {
            item.setAdditionalProperty(property,true);
            dupeSet.add(item.getName());
        }
    }
}
