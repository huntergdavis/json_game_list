package com.hunterdavis.jsongamelist;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.hunterdavis.jsongamelist.types.JsonGameList;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.otto.ThreadEnforcer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;


public class JsonGameListActivity extends ActionBarActivity implements ActionBar.TabListener {

    public static OkHttpClient client = null;
    public static JsonGameList gameList = null;
    // our ui update bus
    public static Bus bus = new Bus(ThreadEnforcer.MAIN);
    private static boolean ignoreIntent = false;
    // our pager adapter
    GamePagerAdapter mGamePagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_game_list);

        bus.register(this);

        Intent intent = getIntent();
        String action = intent.getAction();

        if (ignoreIntent) {
            ignoreIntent = false;
        } else {
            if (action.compareTo(Intent.ACTION_VIEW) == 0) {
                String scheme = intent.getScheme();
                ContentResolver resolver = getContentResolver();

                if (scheme.compareTo(ContentResolver.SCHEME_FILE) == 0) {
                    Uri uri = intent.getData();

                    boolean intentResolutionFailed = false;
                    InputStream input = null;
                    try {
                        input = resolver.openInputStream(uri);
                    } catch (FileNotFoundException e) {
                        intentResolutionFailed = true;
                    }
                    try {
                        gameList = JsonGameListParser.parseInputStreamForJsonResume(input);
                    } catch (UnsupportedEncodingException e) {
                        intentResolutionFailed = true;
                    }

                    if (!intentResolutionFailed) {
                        // this.recreate();
                    }
                }
            }
        }


        // implement our okhttp cache for work page.. ugh ico files..!
        int cacheSize = 1024 * 1024; // 1 MiB
        File cacheDirectory = new File(getCacheDir().getAbsolutePath(), "HttpCache");
        Cache cache = null;
        try {
            cache = new Cache(cacheDirectory, cacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        client = new OkHttpClient();
        client.setCache(cache);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mGamePagerAdapter = new GamePagerAdapter(getSupportFragmentManager(), this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mGamePagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mGamePagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mGamePagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        // just a little niceness
        if (gameList != null) {
            if (gameList.getBasics() != null) {
                if (!TextUtils.isEmpty(gameList.getBasics().getName())) {
                    getSupportActionBar().setTitle(gameList.getBasics().getName());
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_json_game_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_hunter:
                try {
                    gameList = JsonGameListParser.parseHuntersGameList(this);
                    bus.post(new JsonUpdatedEvent(gameList));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.action_github:
                new JsonDownloadTask().execute("https://raw.githubusercontent.com/huntergdavis/json_game_list/master/JsonGameList/app/src/main/assets/gamelist.json");
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Subscribe
    public void updateJson(JsonUpdatedEvent event) {
        if (event.gameList != null) {
            gameList = event.gameList;
            ignoreIntent = true;
            JsonGameListActivity.this.recreate();
        }
    }
}
