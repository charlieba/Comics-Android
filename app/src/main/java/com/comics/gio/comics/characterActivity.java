package com.comics.gio.comics;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by giova on 17/07/2016.
 */
public class characterActivity extends AppCompatActivity {
    PagerAdapterCharcter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_character);

        Bundle b = getIntent().getExtras();
        String detail ="";
        String wiki ="";
        String comics ="";
        if(b != null){
            detail=b.getString("detail");
            wiki=b.getString("wiki");
            comics=b.getString("comics");
        }

        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new PagerAdapterCharcter(getSupportFragmentManager(), detail,wiki, comics);
        vpPager.setOffscreenPageLimit(3);
        vpPager.setAdapter(adapterViewPager);

    }

}
