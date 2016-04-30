package com.example.celebrer;

import android.support.v4.app.ListFragment;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Shrabanti on 03-12-2015.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(android.support.v4.app.FragmentManager fm) {
        super(fm);
    }

    @Override
    public ListFragment getItem(int position) {
        switch(position)
        {
            case 0: return new Wedding();
            case 1: return new Birthday();
            case 2: return new Musical();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0 :
                return "WEDDING";
            case 1 :
                return "BIRTHDAY";
            case 2 :
                return "MUSICAL";
        }
        return null;
    }

}
