package com.hunterdavis.jsongamelistmanager.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

import com.hunterdavis.jsongamelistmanager.JsonGameListActivity;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * We have to do this instead of using picasso.. why?
 * -- because picasso won't render favico.ico files. ugh
 */
public class IconDownloadTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference imageViewReference;
    private String tag;

    public IconDownloadTask(ImageView imageView, String tag) {
        imageViewReference = new WeakReference(imageView);
        this.tag = tag;
    }

    @Override
    // Actual download method, run in the task thread
    protected Bitmap doInBackground(String... params) {
        // params comes from the execute() call: params[0] is the url.
        return getBitmapFromURL(params[0]);
    }

    @Override
    // Once the image is downloaded, associates it to the imageView
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null) {
            ImageView imageView = (ImageView) imageViewReference.get();
            if (imageView != null) {
                if(imageView.getTag() == tag) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                        imageView.setVisibility(View.VISIBLE);
                    }
                }
            }

        }
    }

    public Bitmap getBitmapFromURL(String address) {
        try {
            URL url = new URL(address);

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = JsonGameListActivity.client.newCall(request).execute();
            InputStream input = response.body().byteStream();

            BitmapFactory.Options options = new BitmapFactory.Options();

            Bitmap myBitmap = BitmapFactory.decodeStream(input, null, options);

            return myBitmap;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
