package com.hunterdavis.jsongamelist.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hunterdavis.jsongamelist.JsonGameListActivity;
import com.hunterdavis.jsongamelist.R;
import com.hunterdavis.jsongamelist.types.*;
import com.hunterdavis.jsongamelist.types.System;

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

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SystemFragment newInstance(int systemNumber) {
        SystemFragment fragment = new SystemFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_SYSTEM_NUMER,systemNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SystemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        if(args != null) {
            this.systemNumber = args.getInt(EXTRA_SYSTEM_NUMER);
        }

        systemReference = JsonGameListActivity.gameList.systems.get(systemNumber-1);

        ArrayList dummyList = new ArrayList<Integer>();
        for(int i = 0;i<systemReference.getListItemCount();i++) {
            dummyList.add(69); // bill and ted
        }

        SystemAdapter adapter = new SystemAdapter(
                inflater.getContext(), dummyList);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private class SystemAdapter extends ArrayAdapter<Integer> {

        public SystemAdapter(Context context, List<Integer> items) {
            super(context, R.layout.system_list_item, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
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
                convertView.setTag(viewHolder);
            } else {
                // recycle the already inflated view
                viewHolder = (WorkViewHolder) convertView.getTag();
            }

            // update the item view
            viewHolder.name.setText(systemReference.getSystemListItemName(position));
            viewHolder.revision.setText(systemReference.getSystemListItemRevision(position));
            viewHolder.url.setText(systemReference.getSystemListItemUrl(position));
            viewHolder.releaseDate.setText(systemReference.getSystemListItemReleaseDate(position));
            viewHolder.condition.setText(systemReference.getSystemListItemCondition(position));
            viewHolder.quantity.setText(systemReference.getSystemListItemQuantity(position));
            viewHolder.description.setText(systemReference.getSystemListItemDescription(position));
            viewHolder.systemRequirements.setText(systemReference.getSystemListItemSystemRequirements(position));

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

    }
}
