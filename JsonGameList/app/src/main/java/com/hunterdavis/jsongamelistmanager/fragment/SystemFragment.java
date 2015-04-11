package com.hunterdavis.jsongamelistmanager.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hunterdavis.jsongamelistmanager.tasks.DuckDuckGoTask;
import com.hunterdavis.jsongamelistmanager.tasks.IconDownloadTask;
import com.hunterdavis.jsongamelistmanager.JsonGameListActivity;
import com.hunterdavis.jsongamelistmanager.R;
import com.hunterdavis.jsongamelistmanager.tasks.YoutubePreviewDownloadTask;
import com.hunterdavis.jsongamelistmanager.types.System;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hunter on 3/22/15.
 */
public class SystemFragment extends ListFragment {

    public static final String EXTRA_SYSTEM_NUMER = "com.hunterdavis.jsongamelistmanager.EXTRA_SYSTEM_NUMBER";

    // system number 0 through whatever
    int systemNumber = -1;

    System systemReference = new System();

    public SystemFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SystemFragment newInstance(int systemNumber) {
        SystemFragment fragment = new SystemFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_SYSTEM_NUMER, systemNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            this.systemNumber = args.getInt(EXTRA_SYSTEM_NUMER);
        }

        systemReference = JsonGameListActivity.gameList.systems.get(systemNumber - 1);

        // alphebetize our games list when it's loaded in
        systemReference.alphebetizeGamesList();

        ArrayList dummyList = new ArrayList<Integer>();
        for (int i = 0; i < systemReference.getListItemCount(); i++) {
            dummyList.add(69); // bill and ted
        }

        SystemAdapter adapter = new SystemAdapter(
                inflater.getContext(), dummyList);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void updateItemViewVisibliltyAndText(TextView view, String text) {
        if (TextUtils.isEmpty(text)) {
            view.setVisibility(View.GONE);
            view.setText("");
        } else {
            view.setText(text);
            view.setVisibility(View.VISIBLE);
        }
    }

    private void updateItemViewVisibliltyAndTextWithUrlAndDefaultText(TextView view, String text, final String url) {
        if (TextUtils.isEmpty(url)) {
            view.setVisibility(View.GONE);
            view.setText("");
        } else {
            view.setText(text);
            view.setVisibility(View.VISIBLE);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                }
            });
        }


    }

    private class SystemAdapter extends ArrayAdapter<Integer> {

        public SystemAdapter(Context context, List<Integer> items) {
            super(context, R.layout.system_list_item, items);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final WorkViewHolder viewHolder;
            if (convertView == null) {
                // inflate the GridView item layout
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.system_list_item, parent, false);
                // initialize the view holder
                viewHolder = new WorkViewHolder();
                viewHolder.name = (TextView) convertView.findViewById(R.id.name);
                viewHolder.revision = (TextView) convertView.findViewById(R.id.revision);
                viewHolder.url = (TextView) convertView.findViewById(R.id.url);
                viewHolder.releaseDate = (TextView) convertView.findViewById(R.id.releaseDate);
                viewHolder.condition = (TextView) convertView.findViewById(R.id.condition);
                viewHolder.quantity = (TextView) convertView.findViewById(R.id.quantity);
                viewHolder.description = (TextView) convertView.findViewById(R.id.description);
                viewHolder.systemRequirements = (TextView) convertView.findViewById(R.id.systemRequirementsOrInfo);
                viewHolder.workImageAndWebsiteLauncher = (ImageView) convertView.findViewById(R.id.websiteIconimageButton);
                viewHolder.duplicate = (TextView) convertView.findViewById(R.id.duplicate);
                viewHolder.background = (GridLayout) convertView.findViewById(R.id.background);
                viewHolder.imagePreview = (ImageView) convertView.findViewById(R.id.youtube);
                viewHolder.hoursPlayed = (TextView) convertView.findViewById(R.id.hoursPlayed);
                viewHolder.statsLink = (TextView) convertView.findViewById(R.id.statsLink);
                viewHolder.globalStatsLink = (TextView) convertView.findViewById(R.id.globalStatsLink);
                viewHolder.logo = (ImageView) convertView.findViewById(R.id.logo);
                convertView.setTag(viewHolder);
            } else {
                // recycle the already inflated view
                viewHolder = (WorkViewHolder) convertView.getTag();
            }

            // hide the image first
            viewHolder.workImageAndWebsiteLauncher.setVisibility(View.GONE);

            // update the item view
            updateItemViewVisibliltyAndText(viewHolder.name, systemReference.getSystemListItemName(position));
            updateItemViewVisibliltyAndText(viewHolder.revision, systemReference.getSystemListItemRevision(position));
            updateItemViewVisibliltyAndText(viewHolder.url, systemReference.getSystemListItemUrl(position));
            updateItemViewVisibliltyAndText(viewHolder.releaseDate, systemReference.getSystemListItemReleaseDate(position));
            updateItemViewVisibliltyAndText(viewHolder.condition, systemReference.getSystemListItemCondition(position));
            updateItemViewVisibliltyAndText(viewHolder.quantity, systemReference.getSystemListItemQuantity(position));
            updateItemViewVisibliltyAndText(viewHolder.description, systemReference.getSystemListItemDescription(position));
            updateItemViewVisibliltyAndText(viewHolder.systemRequirements, systemReference.getSystemListItemSystemRequirements(position));

            // new steam items
            updateItemViewVisibliltyAndText(viewHolder.hoursPlayed, systemReference.getSystemListItehoursPlayed(getContext(),position));

            updateItemViewVisibliltyAndTextWithUrlAndDefaultText(viewHolder.statsLink,getString(R.string.stats), systemReference.getSystemListItemStatsLink(position));
            updateItemViewVisibliltyAndTextWithUrlAndDefaultText(viewHolder.globalStatsLink,getString(R.string.global_stats), systemReference.getSystemListItemGlobalStatsLink(position));



            // if duplicate is empty, try cross duplicate, otherwise default to duplicate
            if(TextUtils.isEmpty(systemReference.getSystemListItemDuplicates(position, getString(R.string.duplicate)))) {
                updateItemViewVisibliltyAndText(viewHolder.duplicate, systemReference.getSystemListItemCrossDuplicates(position, getString(R.string.cross_duplicate)));
            }else {
                updateItemViewVisibliltyAndText(viewHolder.duplicate, systemReference.getSystemListItemDuplicates(position, getString(R.string.duplicate)));
            }


            // special case - if duplicate then set background as RED, cross as magenta
            if(TextUtils.isEmpty(viewHolder.duplicate.getText().toString())) {
                viewHolder.background.setBackgroundColor(getResources().getColor(R.color.background_floating_material_light));
            }else if (viewHolder.duplicate.getText().toString().equalsIgnoreCase(getString(R.string.cross_duplicate))) {
                viewHolder.background.setBackgroundColor(Color.MAGENTA);
            }else if (viewHolder.duplicate.getText().toString().equalsIgnoreCase(getString(R.string.duplicate))) {
                viewHolder.background.setBackgroundColor(Color.RED);
            }

            // our favico downloading task if no logoImage
            String logoImage = systemReference.getSystemListItemLogoImage(position);
            String baseURL = "";
            try {
                URL url = new URL(systemReference.getSystemListItemUrl(position));
                baseURL = url.getProtocol() + "://" + url.getHost();
            } catch (MalformedURLException e) {
                // do something.. or not
            }

            viewHolder.workImageAndWebsiteLauncher.setVisibility(View.GONE);
            if(!TextUtils.isEmpty(baseURL) && TextUtils.isEmpty(logoImage)) {
                final String favIconString = baseURL + "/favicon.ico";
                viewHolder.workImageAndWebsiteLauncher.setTag(systemReference.getSystemListItemName(position));
                new IconDownloadTask(viewHolder.workImageAndWebsiteLauncher, systemReference.getSystemListItemName(position)).execute(favIconString);
            }

            if(TextUtils.isEmpty(logoImage)) {
                viewHolder.logo.setVisibility(View.GONE);
            }else {
                viewHolder.logo.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(logoImage).into(viewHolder.logo);
            }


            // the imagePreview or google image search preview image
            viewHolder.imagePreview.setVisibility(View.GONE);
            viewHolder.imagePreview.setTag(systemReference.getSystemListItemName(position));
            String firstYoutubeUrl = systemReference.getSystemListYoutube(position);
            if(!TextUtils.isEmpty(firstYoutubeUrl)) {
                new YoutubePreviewDownloadTask(getActivity(),viewHolder.imagePreview, systemReference.getSystemListItemName(position)).execute(firstYoutubeUrl);
                viewHolder.imagePreview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(systemReference.getSystemListYoutube(position)));
                        startActivity(browserIntent);
                    }
                });
            }else {
                viewHolder.imagePreview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //nada
                    }
                });
            }


            // default to not duck duck gone
            viewHolder.duckDuckWent = false;
            viewHolder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(viewHolder.duckDuckWent = false) {
                        new DuckDuckGoTask(getActivity(), viewHolder.imagePreview, viewHolder.description, systemReference.getSystemListItemName(position)).execute();
                        viewHolder.duckDuckWent = true;
                    }
                }
            });


            // go to website if exists when you click on it
            if (!TextUtils.isEmpty(systemReference.getSystemListItemUrl(position))) {
                viewHolder.url.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(systemReference.getSystemListItemUrl(position)));
                        startActivity(browserIntent);
                    }
                });
            } else {
                viewHolder.url.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // do nothing
                    }
                });
            }


            return convertView;
        }
    }

    /**
     * convenience viewHolder pattern
     */
    private class WorkViewHolder {
        TextView name;
        TextView revision;
        TextView url;
        TextView releaseDate;
        TextView condition;
        TextView quantity;
        TextView duplicate;
        TextView description;
        TextView systemRequirements;
        ImageView workImageAndWebsiteLauncher;
        ImageView imagePreview;
        ImageView logo;
        GridLayout background;

        TextView hoursPlayed;
        TextView statsLink;
        TextView globalStatsLink;

        boolean duckDuckWent;

    }
}
