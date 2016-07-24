package com.comics.gio.comics;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Base64;
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
import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import android.os.StrictMode;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.w3c.dom.Text;

import com.facebook.FacebookSdk;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DrawerLocker  {
    android.support.v4.app.FragmentManager fm;
    MaterialSearchView searchView;
    //String[] suggestion1;
    ArrayList<String> suggestion;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        fm=getSupportFragmentManager();
         if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //HASH KEY
        /*try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.comics.gio.comics", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                System.out.println("KeyHash: "+
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/
        //suggestion=new ArrayList();
        //suggestion1=new String[3000];
        request request=new request();
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

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /*listCharacters lc=new listCharacters();
        FragmentTransaction ftResultados = fm.beginTransaction();
        ftResultados.add(R.id.includeFragment, lc);
        ftResultados.commit();*/
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(true);
        //searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        /*String[] stringArray =  Arrays.copyOf(suggestion.toArray(),suggestion.size(),String[].class);
        searchView.setSuggestions(stringArray);*/
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
                listCharacters lc=new listCharacters();
                FragmentTransaction ftResultados = fm.beginTransaction();
                ftResultados.replace(R.id.includeFragment, lc);
                ftResultados.commit();
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

        if(isLoggedIn()){
            //System.out.println("Usuario esta logueado");
            listCharacters lc=new listCharacters();
            FragmentTransaction ftResultados = fm.beginTransaction();
            ftResultados.add(R.id.includeFragment, lc);
            ftResultados.commit();

            Profile.getCurrentProfile().getId();
           // System.out.println("userid "+Profile.getCurrentProfile().getId());


        }else{
            //System.out.println("Usuario NO esta logueado");
            fragmentLogin lc=new fragmentLogin();
            FragmentTransaction ftResultados = fm.beginTransaction();
            ftResultados.replace(R.id.includeFragment, lc);
            ftResultados.commit();
        }


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
        setProfile(true);
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
            fragmentLogin lc=new fragmentLogin();
            FragmentTransaction ftResultados = fm.beginTransaction();
            ftResultados.replace(R.id.includeFragment, lc);
            ftResultados.commit();
        } else if (id == R.id.nav_gallery) {
            //LoginManager.getInstance().logOut();
        } else if (id == R.id.nav_slideshow) {


        } else if (id == R.id.nav_share) {
            if(isLoggedIn()){
                LoginManager.getInstance().logOut();
                fragmentLogin lc=new fragmentLogin();
                FragmentTransaction ftResultados = fm.beginTransaction();
                ftResultados.replace(R.id.includeFragment, lc);
                ftResultados.commit();
            }

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

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
    public static Bitmap getFacebookProfilePicture(String userID) throws IOException {
        URL imageURL = new URL("https://graph.facebook.com/" + userID + "/picture?type=large");
        Bitmap bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());

        return bitmap;
    }

    @Override
    public void setDrawerEnabled(boolean enabled) {
        int lockMode = enabled ? DrawerLayout.LOCK_MODE_UNLOCKED :
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        drawer.setDrawerLockMode(lockMode);
        toggle.setDrawerIndicatorEnabled(enabled);
    }

    @Override
    public void setProfile(boolean enabled) {
        if(enabled){
            if(isLoggedIn()){
                ImageView iv = (ImageView) findViewById(R.id.imageView);
                Picasso.with(getApplicationContext()).load("https://graph.facebook.com/"+Profile.getCurrentProfile().getId()+"/picture?type=large&width=108").into(iv);
                TextView tvNameFacebook=(TextView) findViewById(R.id.tvNameFacebook);
                tvNameFacebook.setText(Profile.getCurrentProfile().getName());
            }
        }else{
            ImageView iv = (ImageView) findViewById(R.id.imageView);
            TextView tvNameFacebook=(TextView) findViewById(R.id.tvNameFacebook);
            tvNameFacebook.setText("");
        }

    }
}
