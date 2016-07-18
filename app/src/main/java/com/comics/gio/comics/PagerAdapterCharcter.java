package com.comics.gio.comics;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by giova on 17/07/2016.
 */
public class PagerAdapterCharcter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;
    fragmentComicsCharacter cc;
    fragmentDetailCharacter dc;
    fragmentWikiCharacter wc;

    public PagerAdapterCharcter(FragmentManager fragmentManager) {
        super(fragmentManager);
        dc=new fragmentDetailCharacter();
        wc=new fragmentWikiCharacter();
        cc=new fragmentComicsCharacter();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return wc;
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return dc;
            case 2: // Fragment # 1 - This will show SecondFragment
                return cc;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
