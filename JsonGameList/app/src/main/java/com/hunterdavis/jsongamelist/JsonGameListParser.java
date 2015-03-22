package com.hunterdavis.jsongamelist;

import android.content.Context;
import android.content.res.AssetManager;


import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.hunterdavis.jsongamelist.types.JsonGameList;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by hunter on 3/22/15.
 */
public class JsonGameListParser {
    /**
     * parse Hunter's current resume from the assets folder
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
     * @param input
     * @return
     */
    public static JsonGameList parseInputStreamForJsonResume(InputStream input) throws UnsupportedEncodingException {

        JsonReader reader = new JsonReader(new InputStreamReader(input, "UTF-8"));
        Gson gson = new Gson();
        JsonGameList gameList = gson.fromJson(reader, JsonGameList.class);

        return gameList;
    }
}
