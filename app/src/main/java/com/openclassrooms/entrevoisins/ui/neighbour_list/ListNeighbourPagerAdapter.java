package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;


public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "PagerAdapter";

    public ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {

        Log.d(TAG, "getItem: is called");

        // Position is passed through newInstance(position) from getItem(int position)

        return NeighbourFragment.newInstance(position);

        }

    /**
     * get the number of pages
     * @return
     */
    @Override
    public int getCount() {
        Log.d(TAG, "getCount: is called");
        return 2;
    }
}