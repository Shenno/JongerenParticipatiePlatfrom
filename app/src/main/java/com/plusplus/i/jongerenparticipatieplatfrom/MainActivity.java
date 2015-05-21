package com.plusplus.i.jongerenparticipatieplatfrom;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import com.plusplus.i.jongerenparticipatieplatfrom.adapter.NavDrawerListAdapter;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.AddEventToDossierFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.AddExtraToDossierFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.AddLocationToDossierFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.AmsFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.AmsHomeFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.CreateDossierFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.DmsFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.DossierFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.DossiersFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.EditDossierFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.LogInFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.MyProfileFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.MyReactionsFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.OnSelectedListener;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.QuestionFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.ReactionFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.ReactionsFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.RegisterFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.SolutionFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.fragments.VoteFragment;
import com.plusplus.i.jongerenparticipatieplatfrom.model.NavDrawerItem;

public class MainActivity extends Activity implements OnSelectedListener {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();

        // adding nav drawer items to array
        // Waar Lig je wakker van
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Hoe oplossen
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Log in
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        //Registreer
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Mijn profiel
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // Mijn dossiers
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        //Mijn Vragen
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));

        //Extra 2
//        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));
//        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));


        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);

        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            // on first time display view for first nav item
            displayView(0);
        }
    }

    /**
     * Slide menu item click listener
     */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new AmsHomeFragment();
                break;
            case 1:
                fragment = new QuestionFragment();
                break;
            case 2:
                fragment = new LogInFragment();
                break;
            case 3:
                fragment = new RegisterFragment();
                break;
            case 4:
                fragment = new MyProfileFragment();
                break;
            case 5:
                fragment = new VoteFragment();
                break;
            case 6:
                fragment = new MyReactionsFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClicked(int i) {
        Fragment fragment = new DmsFragment();
        Bundle args = new Bundle();
        args.putInt("parameter", i);
        fragment.setArguments(args);
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, fragment);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }

    @Override
    public void onDossierItemClicked(int id) {
        Fragment fragment = new DossierFragment();
        Bundle args = new Bundle();
        args.putInt("dId", id);
        fragment.setArguments(args);
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, fragment);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }

    @Override
    public void onDmsItemClicked(int id, String answer) {
        Fragment fragment = new DossiersFragment();
        Bundle args = new Bundle();
        args.putInt("dId", id);
        args.putString("answer", answer);
        fragment.setArguments(args);
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, fragment);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }

    @Override
    public void onNewDossierClicked(int id) {
        Fragment fragment = new CreateDossierFragment();
        Bundle args = new Bundle();
        args.putInt("dId", id);
        fragment.setArguments(args);
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, fragment);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }

    @Override
    public void onAddExtraToDossier(int id) {
        Fragment fragment = new AddExtraToDossierFragment();
        Bundle args = new Bundle();
        args.putInt("dId", id);
        fragment.setArguments(args);
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, fragment);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }
    @Override
    public void onAddLocationToDossier(int id) {
        Fragment fragment = new AddLocationToDossierFragment();
        Bundle args = new Bundle();
        args.putInt("dId", id);
        fragment.setArguments(args);
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, fragment);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }

    @Override
    public void onAddEventToDossier(int id) {
        Fragment fragment = new AddEventToDossierFragment();
        Bundle args = new Bundle();
        args.putInt("dId", id);
        fragment.setArguments(args);
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, fragment);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }
    @Override
    public void onHomeAmsItemClicked(int i) {
        Fragment fragment = new AmsFragment();
        Bundle args = new Bundle();
        args.putInt("parameter", i);
        fragment.setArguments(args);
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, fragment);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }

    @Override
    public void onAmsItemClicked(int id, String question) {
        Fragment fragment = new ReactionsFragment();
        Bundle args = new Bundle();
        args.putInt("dId", id);
        args.putString("answer", question);
        fragment.setArguments(args);
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, fragment);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }

    @Override
    public void onReactionItemClicked(int id) {
        Fragment fragment = new ReactionFragment();
        Bundle args = new Bundle();
        args.putInt("dId", id);
        fragment.setArguments(args);
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, fragment);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }

    @Override
    public void onVoteDossier(int id) {
        Fragment fragment = new VoteFragment();
        Bundle args = new Bundle();
        args.putInt("dId", id);
        fragment.setArguments(args);
        FragmentManager fragMan = getFragmentManager();
        FragmentTransaction fragTran = fragMan.beginTransaction();
        fragTran.replace(R.id.frame_container, fragment);
        fragTran.addToBackStack(null);
        fragTran.commit();
    }



}
