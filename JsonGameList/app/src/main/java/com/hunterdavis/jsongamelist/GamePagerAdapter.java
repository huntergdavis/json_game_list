package com.hunterdavis.jsongamelist;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.hunterdavis.jsongamelist.fragment.BasicsFragment;

import java.util.Locale;

/**
 * Created by hunter on 3/22/15.
 */
public class GamePagerAdapter  extends FragmentPagerAdapter {

    Context adapterContext;

    public GamePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        adapterContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
            default:
                return BasicsFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        if(JsonGameListActivity.gameList == null) {
            return 1;
        }else {
            return JsonGameListActivity.gameList.getSystemsCount() + 1;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0) {
            return adapterContext.getString(R.string.overview);
        } else {
            if(JsonGameListActivity.gameList == null) {
                return adapterContext.getString(R.string.overview);
            }else {
                return JsonGameListActivity.gameList.getSystemName(position);
            }
        }
    }
}
