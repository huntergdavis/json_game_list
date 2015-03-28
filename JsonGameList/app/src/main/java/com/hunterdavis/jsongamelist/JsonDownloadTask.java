package com.hunterdavis.jsongamelist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.hunterdavis.jsongamelist.types.JsonGameList;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
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
        if (isCancelled()) {
        }

        if(json != null) {
            JsonGameListActivity.bus.post(new JsonUpdatedEvent(json));
        }


    }

    public JsonGameList getJsonFromURL(String address) throws Exception{
            URL url = new URL(address);

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = JsonGameListActivity.client.newCall(request).execute();
            InputStream input = response.body().byteStream();
            return JsonGameListParser.parseInputStreamForJsonResume(input);
     }

}
