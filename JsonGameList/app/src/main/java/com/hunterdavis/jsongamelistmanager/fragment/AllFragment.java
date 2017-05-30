package com.hunterdavis.jsongamelistmanager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hunterdavis.jsongamelistmanager.JsonGameListActivity;
import com.hunterdavis.jsongamelistmanager.types.System;
import com.hunterdavis.jsongamelistmanager.types.SystemItemWithMetadata;

import java.util.ArrayList;

/**
 * Created by hunter on 5/30/17.
 */

public class AllFragment extends SystemFragment {

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AllFragment newInstance() {
        AllFragment fragment = new AllFragment();
        return fragment;
    }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            System superSystem = new System();
            superSystem.name = "All Systems";

            systemReference = superSystem;

            for(System system : JsonGameListActivity.gameList.systems) {
                superSystem.consoles.addAll(system.consoles);
                superSystem.accessories.addAll(system.accessories);
                superSystem.games.addAll(system.games);
                superSystem.movies.addAll(system.movies);
                superSystem.music.addAll(system.music);
            }


            //systemReference = JsonGameListActivity.gameList.systems.get(systemNumber - 1);

            // alphebetize our games list when it's loaded in
            systemReference.alphabetizeSystemItemLists();

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
}
