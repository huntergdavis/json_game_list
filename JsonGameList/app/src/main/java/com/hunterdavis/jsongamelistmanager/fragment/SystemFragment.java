package com.hunterdavis.jsongamelistmanager.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Movie;
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
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.hunterdavis.jsongamelistmanager.JsonGameListParser;
import com.hunterdavis.jsongamelistmanager.tasks.DuckDuckGoTask;
import com.hunterdavis.jsongamelistmanager.tasks.IconDownloadTask;
import com.hunterdavis.jsongamelistmanager.JsonGameListActivity;
import com.hunterdavis.jsongamelistmanager.R;
import com.hunterdavis.jsongamelistmanager.tasks.YoutubePreviewDownloadTask;
import com.hunterdavis.jsongamelistmanager.types.Accessory;
import com.hunterdavis.jsongamelistmanager.types.Console;
import com.hunterdavis.jsongamelistmanager.types.Game;
import com.hunterdavis.jsongamelistmanager.types.System;
import com.hunterdavis.jsongamelistmanager.types.SystemItemWithMetadata;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
        systemReference.alphabetizeGameList();

        ArrayList systemItemList = new ArrayList<SystemItemWithMetadata>();

        systemItemList.add(systemReference);
        systemItemList.addAll(systemReference.consoles);
        systemItemList.addAll(systemReference.accessories);
        systemItemList.addAll(systemReference.games);
        systemItemList.addAll(systemReference.movies);
        systemItemList.addAll(systemReference.music);

        SystemAdapter adapter = new SystemAdapter(
                inflater.getContext(), systemItemList);

        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getListView().setFastScrollEnabled(true);
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

    private class SystemAdapter extends ArrayAdapter<SystemItemWithMetadata>  {


        public SystemAdapter(Context context, List<SystemItemWithMetadata> items) {
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
                viewHolder.language = (TextView) convertView.findViewById(R.id.language);
                convertView.setTag(viewHolder);
            } else {
                // recycle the already inflated view
                viewHolder = (WorkViewHolder) convertView.getTag();
            }

            // hide the image first
            viewHolder.workImageAndWebsiteLauncher.setVisibility(View.GONE);

            final SystemItemWithMetadata currentItem = getItem(position);

            // update the item view
            updateItemViewVisibliltyAndText(viewHolder.name, currentItem.name);
            updateItemViewVisibliltyAndText(viewHolder.revision, currentItem.revision);
            updateItemViewVisibliltyAndText(viewHolder.url, currentItem.url);
            updateItemViewVisibliltyAndText(viewHolder.releaseDate, currentItem.releaseDate);
            updateItemViewVisibliltyAndText(viewHolder.condition, currentItem.condition);
            updateItemViewVisibliltyAndText(viewHolder.quantity, currentItem.getDescriptiveQuantity(getContext()));
            updateItemViewVisibliltyAndText(viewHolder.description, currentItem.description);
            updateItemViewVisibliltyAndText(viewHolder.language, currentItem.language);


            // swap revision if system
            if(currentItem instanceof System) {
                updateItemViewVisibliltyAndText(viewHolder.revision,currentItem.getRevision() );
            }


            updateItemViewVisibliltyAndText(viewHolder.systemRequirements, getSystemListItemSystemRequirements(currentItem));

            // new steam items
            updateItemViewVisibliltyAndText(viewHolder.hoursPlayed, getSystemListItemhoursPlayed(getContext(), currentItem));

            updateItemViewVisibliltyAndTextWithUrlAndDefaultText(viewHolder.statsLink,getString(R.string.stats), getSystemListItemStatsLink(currentItem));
            updateItemViewVisibliltyAndTextWithUrlAndDefaultText(viewHolder.globalStatsLink,getString(R.string.global_stats), getSystemListItemGlobalStatsLink(currentItem));



            // if duplicate is empty, try cross duplicate, otherwise default to duplicate
            if(TextUtils.isEmpty(getSystemListItemDuplicates(currentItem, getString(R.string.duplicate)))) {
                updateItemViewVisibliltyAndText(viewHolder.duplicate, currentItem.getStringIfProperty(JsonGameListParser.PROPERTY_DUPLICATE_OTHER_CONSOLE,getString(R.string.cross_duplicate)));
            }else {
                updateItemViewVisibliltyAndText(viewHolder.duplicate, currentItem.getStringIfProperty(JsonGameListParser.PROPERTY_DUPLICATE,getString(R.string.duplicate)));
            }


            // special case - if duplicate then set background as RED, cross as magenta
            if(TextUtils.isEmpty(viewHolder.duplicate.getText().toString())) {
                viewHolder.background.setBackgroundColor(getResources().getColor(R.color.background_floating_material_light));
            }else if (viewHolder.duplicate.getText().toString().equalsIgnoreCase(getString(R.string.cross_duplicate))) {
                viewHolder.background.setBackgroundColor(Color.MAGENTA);
            }else if (viewHolder.duplicate.getText().toString().equalsIgnoreCase(getString(R.string.duplicate))) {
                viewHolder.background.setBackgroundColor(Color.RED);
            }

            if (currentItem.getQuantity() < 1) {
                viewHolder.background.setBackgroundColor(Color.GRAY);
            }

            // our favico downloading task if no logoImage
            String logoImage = getSystemListItemLogoImage(currentItem);
            String baseURL = "";
            try {
                URL url = new URL(getSystemListItemUrl(currentItem));
                baseURL = url.getProtocol() + "://" + url.getHost();
            } catch (MalformedURLException e) {
                // do something.. or not
            }

            viewHolder.workImageAndWebsiteLauncher.setVisibility(View.GONE);
            if(!TextUtils.isEmpty(baseURL) && TextUtils.isEmpty(logoImage)) {
                final String favIconString = baseURL + "/favicon.ico";
                viewHolder.workImageAndWebsiteLauncher.setTag(currentItem.name);
                new IconDownloadTask(viewHolder.workImageAndWebsiteLauncher, currentItem.name).execute(favIconString);
            }

            if(TextUtils.isEmpty(logoImage)) {
                viewHolder.logo.setVisibility(View.GONE);
            }else {
                viewHolder.logo.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(logoImage).into(viewHolder.logo);
            }


            // the imagePreview or google image search preview image
            viewHolder.imagePreview.setVisibility(View.GONE);
            viewHolder.imagePreview.setTag(currentItem.name);
            String firstYoutubeUrl = getSystemListYoutube(currentItem);
            if(!TextUtils.isEmpty(firstYoutubeUrl)) {
                new YoutubePreviewDownloadTask(getActivity(),viewHolder.imagePreview, currentItem.name).execute(firstYoutubeUrl);
                viewHolder.imagePreview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getSystemListYoutube(currentItem)));
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
                    if(!viewHolder.duckDuckWent) {
                        new DuckDuckGoTask(getActivity(), viewHolder.imagePreview, viewHolder.description, currentItem.name).execute();
                        viewHolder.duckDuckWent = true;
                    }
                }
            });


            // go to website if exists when you click on it
            if (!TextUtils.isEmpty(getSystemListItemUrl(currentItem))) {
                viewHolder.url.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getSystemListItemUrl(currentItem)));
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
        TextView language;

        boolean duckDuckWent;

    }

    public String getSomethingFromSomething(SystemItemWithMetadata item) {
        String retString = "";

        if(item instanceof Console) {

        }else if(item instanceof Accessory) {

        }else if(item instanceof Game) {

        }else if(item instanceof SystemItemWithMetadata) { //movie or music case

        }

        return retString;
    }


    public String getSystemListItemSystemRequirements(SystemItemWithMetadata item) {
        String retString = "";

        if(item instanceof Console) {
            retString = ((Console) item).getSystemInfo().toString();
        }else if(item instanceof Accessory) {
            retString = ((Accessory) item).systemRequirements.toString();
        }else if(item instanceof Game) {
            retString = ((Game) item).getSystemRequirements().toString();
        }else  { //movie or music case
            // no system requirements for music and movies on a system right?
        }

        return retString;
    }


    public String getSystemListItemhoursPlayed(Context context, SystemItemWithMetadata item) {
        String retString = "";

        if(item instanceof Console) {

        }else if(item instanceof Accessory) {

        }else if(item instanceof Game) { //movie or music case
            if(!TextUtils.isEmpty(((Game) item).hoursOnRecord))
            {
                retString += ((Game)item).hoursOnRecord + " " + context.getString(R.string.hours_played);
            }

            if(!TextUtils.isEmpty(((Game)item).hoursLast2Weeks)) {
                retString  += " (" + ((Game)item).hoursLast2Weeks + " " + context.getString(R.string.last_two_weeks)+ ")";
            }
        }

        return retString;
    }

    public String getSystemListItemStatsLink(SystemItemWithMetadata item) {
        if(item instanceof Game) { //movie or music case
            return ((Game) item).statsLink;
        }

        return "";
    }


    public String getSystemListItemGlobalStatsLink(SystemItemWithMetadata item) {
        if(item instanceof Game) { //movie or music case
            return ((Game) item).globalStatsLink;
        }

        return "";
    }


    public String getSystemListItemDuplicates(SystemItemWithMetadata item, String errorString) {
        String retString = "";

        if(item.getAdditionalProperties().containsKey(JsonGameListParser.PROPERTY_DUPLICATE)) {
            return errorString;
        }

        return "";
    }



    public String getSystemListItemLogoImage(SystemItemWithMetadata item) {
        return item.logo;
    }



    public String getSystemListItemUrl(SystemItemWithMetadata item) {
        return item.url;
    }


    public String getSystemListYoutube(SystemItemWithMetadata item) {
        if(item.videos.size() > 0) {
            return item.videos.get(0);
        }
       return "";
    }
}
