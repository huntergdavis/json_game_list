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
        Set<String> crossConsoleNames = new HashSet<String>();
        Set<String> crossAccessoryNames = new HashSet<String>();
        Set<String> crossGameNames = new HashSet<String>();


        for (System system : gameList.systems) {
            if(!systemNames.add(system.getName())) {
                system.setAdditionalProperty(PROPERTY_DUPLICATE,true);
            }

            Set<String> consoleNames = new HashSet<String>();
            for (Console console : system.consoles) {
                if(!consoleNames.add(console.getName())) {
                    console.setAdditionalProperty(PROPERTY_DUPLICATE,true);
                }
            }

            Set<String> accessoryNames = new HashSet<String>();
            for (Accessory acc : system.accessories) {
                if(!accessoryNames.add(acc.getName())) {
                    acc.setAdditionalProperty(PROPERTY_DUPLICATE,true);
                }
            }

            Set<String> gameNames = new HashSet<String>();
            for (Game game : system.games) {
                if(!gameNames.add(game.getName())) {
                    game.setAdditionalProperty(PROPERTY_DUPLICATE,true);
                }
            }
        }

        return gameList;
    }
}
