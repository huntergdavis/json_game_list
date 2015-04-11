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

        Set<String> consoleNamesDupes = new HashSet<String>();
        Set<String> accessoryNamesDupes = new HashSet<String>();
        Set<String> gameNamesDupes = new HashSet<String>();
        Set<String> crossGameNamesDupes = new HashSet<String>();
        Set<String> systemNamesDupes = new HashSet<String>();


        // first loop, mark all dupes
        for (System system : gameList.systems) {
            updateSetRefAndDupRef(system, systemNames, systemNamesDupes);

            Set<String> consoleNames = new HashSet<String>();
            for (Console console : system.consoles) {
                updateSetRefAndDupRef(console, consoleNames, consoleNamesDupes);
            }

            Set<String> accessoryNames = new HashSet<String>();
            for (Accessory acc : system.accessories) {
                updateSetRefAndDupRef(acc, accessoryNames, accessoryNamesDupes);
            }

            Set<String> gameNames = new HashSet<String>();
            for (Game game : system.games) {
                updateSetRefAndDupRef(game, gameNames, gameNamesDupes);
                updateSetRefAndDupRef(game, crossGameNames, crossGameNamesDupes);
            }
        }

        // second loop, mark dupes of dupes
        for (System system : gameList.systems) {
            updateSetRefAndDupRef(system, systemNamesDupes, systemNamesDupes);

            for (Console console : system.consoles) {
                updateSetRefAndDupRef(console, consoleNamesDupes, consoleNamesDupes);
            }

            for (Accessory acc : system.accessories) {
                updateSetRefAndDupRef(acc, accessoryNamesDupes, accessoryNamesDupes);
            }

            for (Game game : system.games) {
                updateSetRefAndDupRef(game, gameNamesDupes, gameNamesDupes);
                updateSetRefAndDupRefCrossDupes(game, crossGameNamesDupes, crossGameNamesDupes);
            }
        }

        return gameList;
    }

    public static void updateSetRefAndDupRefCrossDupes(ObjectWithAdditionalProperty oWap, Set<String> setRef, Set<String> dupeRef) {
        if(setRef.contains(oWap.getName())) {
            oWap.setAdditionalProperty(PROPERTY_DUPLICATE_OTHER_CONSOLE,true);
        }
    }

    public static void updateSetRefAndDupRef(ObjectWithAdditionalProperty oWap, Set<String> setRef, Set<String> dupeRef) {
        if(!setRef.add(oWap.getName())) {
            oWap.setAdditionalProperty(PROPERTY_DUPLICATE,true);
            dupeRef.add(oWap.getName());
        }
    }

}
