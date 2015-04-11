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
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hunter on 3/22/15.
 */
public class JsonGameListParser {

    public static final String PROPERTY_DUPLICATE = "duplicate";
    public static final String PROPERTY_DUPLICATE_OTHER_CONSOLE = "duplicate_other_console";

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

        return markDuplicatesInGameList(gameList);
    }




    private static JsonGameList markDuplicatesInGameList(JsonGameList gameList) {
        Set<String> systemNames = new HashSet<String>();
        Set<String> crossGameNames = new HashSet<String>();
        Set<String> crossGameNamesDupes = new HashSet<String>();
        Set<String> systemNamesDupes = new HashSet<String>();


        // first loop, mark all dupes
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

            // second loop, mark dupes of dupes
            for (Console console : system.consoles) {
                updateSetRefSecondPass(console, consoleNamesDupes);
            }

            for (Accessory acc : system.accessories) {
                updateSetRefSecondPass(acc, accessoryNamesDupes);
            }

            for (Game game : system.games) {
                updateSetRefSecondPass(game, gameNamesDupes);
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

    public static void setCrossDupeIfCollision(ObjectWithAdditionalProperty oWap, Set<String> setRef) {
        if(setRef.contains(oWap.getName())) {
            oWap.setAdditionalProperty(PROPERTY_DUPLICATE_OTHER_CONSOLE,true);
        }
    }

    public static void updateSetRefSecondPass(ObjectWithAdditionalProperty oWap, Set<String> setRef) {
        if(setRef.contains(oWap.getName())) {
            oWap.setAdditionalProperty(PROPERTY_DUPLICATE,true);
        }
    }

    public static void setDuplicateAndAddToDupeListIfCollision(ObjectWithAdditionalProperty oWap, Set<String> setRef, Set<String> dupeRef) {
        if(!setRef.add(oWap.getName())) {
            oWap.setAdditionalProperty(PROPERTY_DUPLICATE,true);
            dupeRef.add(oWap.getName());
        }
    }

    public static void setCrossDuplicateAndAddToDupeListIfCollision(ObjectWithAdditionalProperty oWap, Set<String> setRef, Set<String> dupeRef) {
        if(!setRef.add(oWap.getName())) {
            oWap.setAdditionalProperty(PROPERTY_DUPLICATE_OTHER_CONSOLE,true);
            dupeRef.add(oWap.getName());
        }
    }
}
