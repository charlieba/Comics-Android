package com.comics.gio.comics;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by giova on 17/07/2016.
 */
public class characterActivity extends AppCompatActivity {
    PagerAdapterCharcter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_character);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new PagerAdapterCharcter(getSupportFragmentManager());
        vpPager.setOffscreenPageLimit(3);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        vpPager.setAdapter(adapterViewPager);
        indicator.setViewPager(vpPager);
    }

}
