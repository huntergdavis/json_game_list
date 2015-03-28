package com.hunterdavis.jsongamelist.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hunterdavis.jsongamelist.IconDownloadTask;
import com.hunterdavis.jsongamelist.JsonGameListActivity;
import com.hunterdavis.jsongamelist.R;
import com.hunterdavis.jsongamelist.types.System;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hunter on 3/22/15.
 */
public class SystemFragment extends ListFragment {

    public static final String EXTRA_SYSTEM_NUMER = "com.hunterdavis.jsongamelist.EXTRA_SYSTEM_NUMBER";

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


            String baseURL = "";

            try {
                URL url = new URL(systemReference.getSystemListItemUrl(position));
                baseURL = url.getProtocol() + "://" + url.getHost();
            } catch (MalformedURLException e) {
                // do something.. or not
            }

            final String favIconString = baseURL + "/favicon.ico";

            new IconDownloadTask(viewHolder.workImageAndWebsiteLauncher).execute(favIconString);


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
        TextView description;
        TextView systemRequirements;
        ImageView workImageAndWebsiteLauncher;

    }
}
