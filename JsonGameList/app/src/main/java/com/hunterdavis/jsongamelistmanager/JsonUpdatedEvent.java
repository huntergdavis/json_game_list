package com.hunterdavis.jsongamelistmanager;

import com.hunterdavis.jsongamelistmanager.types.JsonGameList;

/**
 * Created by hunter on 3/28/15.
 */
public class JsonUpdatedEvent {

    JsonGameList gameList;
    boolean loadFailed = false;

    public JsonUpdatedEvent(JsonGameList gameList) {
        this.gameList = gameList;
    }

    public JsonUpdatedEvent() {
        loadFailed = true;
    }
}
