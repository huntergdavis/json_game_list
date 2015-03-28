package com.hunterdavis.jsongamelistmanager;

import android.os.AsyncTask;

import com.hunterdavis.jsongamelistmanager.types.JsonGameList;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.net.URL;

/**
 * Created by hunter on 3/28/15.
 */
public class JsonDownloadTask extends AsyncTask<String, Void, JsonGameList> {

    public JsonDownloadTask() {
        JsonGameListActivity.bus.register(this);
    }

    @Override
    // Actual download method, run in the task thread
    protected JsonGameList doInBackground(String... params) {
        // params comes from the execute() call: params[0] is the url.
        try {
            return getJsonFromURL(params[0]);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    // Once the json is downloaded, post it to our ui bus
    protected void onPostExecute(JsonGameList json) {
        if (json != null) {
            JsonGameListActivity.bus.post(new JsonUpdatedEvent(json));
        } else {
            JsonGameListActivity.bus.post(new JsonUpdatedEvent());
        }
    }

    public JsonGameList getJsonFromURL(String address) throws Exception {
        URL url = new URL(address);

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = JsonGameListActivity.client.newCall(request).execute();
        return JsonGameListParser.parseInputStreamForJsonResume(response.body().byteStream());
    }

}
