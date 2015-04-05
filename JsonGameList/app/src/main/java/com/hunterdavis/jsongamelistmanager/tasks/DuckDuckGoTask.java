package com.hunterdavis.jsongamelistmanager.tasks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.hunterdavis.jsongamelistmanager.JsonGameListActivity;
import com.hunterdavis.jsongamelistmanager.types.DuckDuckGo;
import com.hunterdavis.jsongamelistmanager.types.JsonGameList;
import com.hunterdavis.jsongamelistmanager.types.Youtube;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by hunter on 3/29/15.
 */
public class DuckDuckGoTask  extends AsyncTask<String, Void, DuckDuckGo> {
    private final WeakReference imageViewReference;
    private final WeakReference textViewReference;
    private String tag;
    private Context context;

    public DuckDuckGoTask(Context context, ImageView imageView, TextView textview, String tag) {
        imageViewReference = new WeakReference(imageView);
        textViewReference = new WeakReference(textview);
        this.tag = tag;
        this.context = context;
    }

    @Override
    // Actual download method, run in the task thread
    protected DuckDuckGo doInBackground(String... params) {
        // params comes from the execute() call: params[0] is the url.
        return getDuckDuckGoInfo();
    }

    @Override
    // Once the image is downloaded, associates it to the imageView
    protected void onPostExecute(final DuckDuckGo duckDuckInfo) {
        if(isCancelled()) {
            return;
        }
        if(!TextUtils.isEmpty(duckDuckInfo.Image)) {
            if (imageViewReference != null) {
                ImageView imageView = (ImageView) imageViewReference.get();
                if (imageView != null) {
                    if (imageView.getTag() == tag) {
                        imageView.setVisibility(View.VISIBLE);
                        Picasso.with(context)
                                .load(duckDuckInfo.Image)
                                .into(imageView);
                    }
                }
            }
        }

        if(textViewReference != null) {
            TextView textView = (TextView) textViewReference.get();
            if(textView != null) {
                if(TextUtils.isEmpty(duckDuckInfo.Abstract)) {
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }else {
                    String ogText = textView.getText().toString();
                    String textForTextView = ogText + "\n(Results from DuckDuckGo)\n";
                    textForTextView += duckDuckInfo.Abstract;
                    textView.setText(textForTextView);
                    textView.setVisibility(View.VISIBLE);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!TextUtils.isEmpty(duckDuckInfo.AbstractURL)) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(duckDuckInfo.AbstractURL));
                                context.startActivity(browserIntent);
                            }
                        }
                    });
                }
            }
        }
    }

    public DuckDuckGo getDuckDuckGoInfo() {
        try {
            String prefix = "http://api.duckduckgo.com/?q=";
            String suffix = "&format=json&t=jsongamelistmanagerandroid";
            String fullUrl = prefix + URLEncoder.encode(tag, "UTF-8") + suffix;
            URL url = new URL(fullUrl);

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = JsonGameListActivity.client.newCall(request).execute();
            InputStream input = response.body().byteStream();

            JsonReader reader = new JsonReader(new InputStreamReader(input, "UTF-8"));
            Gson gson = new Gson();
            DuckDuckGo duck = gson.fromJson(reader, DuckDuckGo.class);

            return duck;

        } catch (Exception e) {
            return new DuckDuckGo();
        }
    }

}