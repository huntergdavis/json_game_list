package com.hunterdavis.jsongamelist.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hunterdavis.jsongamelist.JsonGameListActivity;
import com.hunterdavis.jsongamelist.R;
import com.hunterdavis.jsongamelist.types.Basics;
import com.squareup.picasso.Picasso;

/**
 * Created by hunter on 3/22/15.
 */
public class BasicsFragment extends Fragment {

    public BasicsFragment() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static BasicsFragment newInstance() {
        BasicsFragment fragment = new BasicsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_basics, container, false);

        if (JsonGameListActivity.gameList != null) {
            final Basics basics = JsonGameListActivity.gameList.getBasics();
            if (null != basics) {
                ImageView profileImage = (ImageView) rootView.findViewById(R.id.profileImage);
                if (!TextUtils.isEmpty(basics.getPicture())) {
                    if (profileImage != null) {
                        profileImage.setVisibility(View.VISIBLE);
                        Picasso.with(container.getContext())
                                .load(basics.getPicture())
                                .into(profileImage);
                    }
                } else {
                    profileImage.setVisibility(View.GONE);
                }

                // compose email with email click
                if (!TextUtils.isEmpty(basics.getEmail())) {
                    TextView tv = (TextView) rootView.findViewById(R.id.email);
                    if (tv != null) {
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                        "mailto", basics.getEmail(), null));
                                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                            }
                        });
                    }
                }

                // load view data if exist, hide if not
                loadifExistsHideIfNot(rootView, R.id.email, basics.getEmail());
                loadifExistsHideIfNot(rootView, R.id.label, basics.getLabel());
                loadifExistsHideIfNot(rootView, R.id.name, basics.getName());
                loadifExistsHideIfNot(rootView, R.id.phone, basics.getPhone());
                loadifExistsHideIfNot(rootView, R.id.summary, basics.getSummary());
                loadifExistsHideIfNot(rootView, R.id.website, basics.getWebsite());

                if (basics.getLocation() != null) {
                    loadifExistsHideIfNot(rootView, R.id.location, basics.getLocation().toString());
                } else {
                    TextView view = (TextView) rootView.findViewById(R.id.location);
                    if (view != null) {
                        view.setVisibility(View.GONE);
                    }

                }


                // go to website if exists when you click on it
                if (!TextUtils.isEmpty(basics.getWebsite())) {
                    TextView tv = (TextView) rootView.findViewById(R.id.website);
                    if (tv != null) {
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(basics.getWebsite()));
                                startActivity(browserIntent);
                            }
                        });
                    }
                }
            }
        }

        return rootView;
    }

    public void loadifExistsHideIfNot(View container, int id, String item) {
        TextView view = (TextView) container.findViewById(id);

        if (view != null) {
            if (TextUtils.isEmpty(item)) {
                view.setVisibility(View.GONE);
                return;
            }

            view.setVisibility(View.VISIBLE);
            view.setText(item);
        }
    }
}
