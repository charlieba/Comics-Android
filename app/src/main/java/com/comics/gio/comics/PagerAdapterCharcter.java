package com.comics.gio.comics;

import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by giova on 17/07/2016.
 */
public class PagerAdapterCharcter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 4;
    /*fragmentComicsCharacter cc;
    fragmentDetailCharacter dc;
    fragmentWikiCharacter wc;*/
    fragmentDetailCharacter cc;
    fragmentDetailCharacter dc;
    fragmentDetailCharacter wc;
    fragmentCharacterHome ch;
    boolean existsWiki=false;
    String[] titlePages={"WIKI","DETAIL","COMICS",""};
    public PagerAdapterCharcter(FragmentManager fragmentManager, String detail, String wiki, String comics, String thumbnail, String description) {
        super(fragmentManager);
        /*dc=new fragmentDetailCharacter();
        wc=new fragmentWikiCharacter();
        cc=new fragmentComicsCharacter();*/
        dc=new fragmentDetailCharacter(detail);
        wc=new fragmentDetailCharacter(wiki);
        cc=new fragmentDetailCharacter(comics);
        ch=new fragmentCharacterHome(thumbnail,description);
        if(wiki!="" && !wiki.isEmpty() && wiki!=null){
            NUM_ITEMS=4;
            existsWiki=true;
            titlePages[0]="WIKI";
            titlePages[1]="DETAIL";
            titlePages[2]="COMICS";
            titlePages[3]="HERO";
        }else{
            existsWiki=false;
            NUM_ITEMS=3;
            titlePages[0]="DETAIL";
            titlePages[1]="COMICS";
            titlePages[2]="HERO";
        }
    }

    @Override
    public Fragment getItem(int position) {
        if(existsWiki){

            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return wc;
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return dc;
                case 2: // Fragment # 1 - This will show SecondFragment
                    return cc;
                case 3: // Fragment # 1 - This will show SecondFragment
                    return ch;
                default:
                    return null;
            }
        }else{
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return dc;
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return cc;
                case 2: // Fragment # 0 - This will show FirstFragment different title
                    return ch;
                default:
                    return null;
            }
        }

    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlePages[position];
    }
}
