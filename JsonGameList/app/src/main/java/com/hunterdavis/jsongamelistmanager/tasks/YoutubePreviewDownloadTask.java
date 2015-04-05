package com.hunterdavis.jsongamelistmanager.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.hunterdavis.jsongamelistmanager.JsonGameListActivity;
import com.hunterdavis.jsongamelistmanager.types.JsonGameList;
import com.hunterdavis.jsongamelistmanager.types.Youtube;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * Created by hunter on 3/29/15.
 */
public class YoutubePreviewDownloadTask  extends AsyncTask<String, Void, String> {
    private final WeakReference imageViewReference;
    private String tag;
    private Context context;

    public YoutubePreviewDownloadTask(Context context, ImageView imageView, String tag) {
        imageViewReference = new WeakReference(imageView);
        this.tag = tag;
        this.context = context;
    }

    @Override
    // Actual download method, run in the task thread
    protected String doInBackground(String... params) {
        // params comes from the execute() call: params[0] is the url.
        return getImagePreviewFromYoutubeUrl(params[0]);
    }

    @Override
    // Once the image is downloaded, associates it to the imageView
    protected void onPostExecute(String youtubePreview) {
        if(isCancelled()) {
            return;
        }
        if (imageViewReference != null) {
            ImageView imageView = (ImageView) imageViewReference.get();
            if (imageView != null) {
                if(imageView.getTag() == tag) {
                    imageView.setVisibility(View.VISIBLE);
                    Picasso.with(context)
                            .load(youtubePreview)
                            .into(imageView);
                }
            }

        }
    }

    public String getImagePreviewFromYoutubeUrl(String address) {
        try {
            String prefix = "http://www.youtube.com/oembed?url=";
            String suffix = "&format=json";
            String fullUrl = prefix + address + suffix;


            URL url = new URL(fullUrl);

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = JsonGameListActivity.client.newCall(request).execute();
            InputStream input = response.body().byteStream();

            JsonReader reader = new JsonReader(new InputStreamReader(input, "UTF-8"));
            Gson gson = new Gson();
            Youtube youtube = gson.fromJson(reader, Youtube.class);

            return youtube.thumbnail_url;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}