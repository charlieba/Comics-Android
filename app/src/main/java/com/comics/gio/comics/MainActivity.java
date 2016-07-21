package com.comics.gio.comics;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.comics.gio.comics.utils.request;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import android.os.StrictMode;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    android.support.v4.app.FragmentManager fm;
    MaterialSearchView searchView;
    //String[] suggestion1;
    ArrayList<String> suggestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm=getSupportFragmentManager();
         if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        suggestion=new ArrayList();
        //suggestion1=new String[3000];
        request request=new request();
        try {
            String[] url={"http://www.comicscharacter.com/get_character?"};
            String respuesta= request.get_request(url);
            JSONArray jsonCharacters = new JSONArray(respuesta.toString());
            for (int i=0;i<jsonCharacters.length();i++){
               suggestion.add(jsonCharacters.getJSONObject(i).get("name").toString());
                //suggestion1[i]=jsonCharacters.getJSONObject(i).get("name").toString();
            }
            System.out.println(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        listCharacters lc=new listCharacters();
        FragmentTransaction ftResultados = fm.beginTransaction();
        ftResultados.add(R.id.includeFragment, lc);
        ftResultados.commit();
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(true);
        //searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        String[] stringArray =  Arrays.copyOf(suggestion.toArray(),suggestion.size(),String[].class);
        searchView.setSuggestions(stringArray);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query!="" && query!=null && query!=" "){
                    query=query.replace(" ","%20");
                    listCharacters lc=new listCharacters();
                    Bundle bundle = new Bundle();
                    bundle.putString("searchQuery",query);
                    lc.setArguments(bundle);
                    FragmentTransaction ftResultados = fm.beginTransaction();
                    ftResultados.replace(R.id.includeFragment, lc);
                    ftResultados.commit();
                    return true;
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText!="" && !newText.equalsIgnoreCase("") && newText!=null){
                    newText=newText.replace(" ","%20");
                    listCharacters lc=new listCharacters();
                    Bundle bundle = new Bundle();
                    bundle.putString("searchQuery",newText);
                    lc.setArguments(bundle);
                    FragmentTransaction ftResultados = fm.beginTransaction();
                    ftResultados.replace(R.id.includeFragment, lc);
                    ftResultados.commit();
                    return true;
                }
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(parent.getItemAtPosition(position));
                //Do some magic
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_search) {
            onSearchRequested();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
           /* Intent intent = new Intent(this, listCharacters.class);
            startActivity(intent);*/
            //startActivity(new Intent(this, listCharacters.class));
            fragmentWikiCharacter lc=new fragmentWikiCharacter();
            FragmentTransaction ftResultados = fm.beginTransaction();
            ftResultados.add(R.id.includeFragment, lc);
            ftResultados.commit();

        } else if (id == R.id.nav_slideshow) {
            fragmentDetailCharacter lc=new fragmentDetailCharacter();
            FragmentTransaction ftResultados = fm.beginTransaction();
            ftResultados.add(R.id.includeFragment, lc);
            ftResultados.commit();

        } else if (id == R.id.nav_manage) {
            fragmentComicsCharacter lc=new fragmentComicsCharacter();
            FragmentTransaction ftResultados = fm.beginTransaction();
            ftResultados.add(R.id.includeFragment, lc);
            ftResultados.commit();

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(this, characterActivity.class);
            startActivity(intent);
            startActivity(new Intent(this, listCharacters.class));

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
