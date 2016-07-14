package com.comics.gio.comics;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.comics.gio.comics.utils.request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class listCharacters extends Fragment {
    ArrayList<itemCharacter> characters;
    RecyclerView rv;
    LinearLayoutManager llm;
    adapterCardViewCharacter adapter;

    /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_characters);
        characters=new ArrayList<itemCharacter>();
        RecyclerView rv = (RecyclerView)findViewById(R.id.rvCharacter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        itemCharacter item= new itemCharacter();
        item.setName("iron-man");
        item.setThumbnail("http://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55.jpg");
        characters.add(item);
        item= new itemCharacter();
        item.setName("thor");
        item.setThumbnail("http://i.annihil.us/u/prod/marvel/i/mg/2/03/52321948a51f2.jpg");
        characters.add(item);
        adapterCardViewCharacter adapter = new adapterCardViewCharacter(this,characters);
        rv.setAdapter(adapter);

    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_list_characters, container, false);
        //rootView.setTag(TAG);

        rv = (RecyclerView) rootView.findViewById(R.id.rvCharacter);

        /*itemCharacter item= new itemCharacter();
        item.setName("iron-man");
        item.setThumbnail("http://i.annihil.us/u/prod/marvel/i/mg/9/c0/527bb7b37ff55.jpg");
        characters.add(item);
        item= new itemCharacter();
        item.setName("thor");
        item.setThumbnail("http://i.annihil.us/u/prod/marvel/i/mg/2/03/52321948a51f2.jpg");
        characters.add(item);*/
        /*adapter = new adapterCardViewCharacter(getActivity(),characters);
        rv.setAdapter(adapter);*/
        String[] params={"http://www.comicscharacter.com/get_character?"};
        new HttpAsyncTask().execute(params);
        return  rootView;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        request r=new request();
        @Override
        protected String doInBackground(String... urls) {
            try {
                return r.get_request(urls);
            } catch (IOException e) {
                e.printStackTrace();
                return  e.getMessage();
            }
        }
        @Override
        protected void onPostExecute(String result) {
            //System.out.println("Resultados :"+result);
            //JSONObject json = null;
            try {
                //json = new JSONObject(result);
                characters=new ArrayList<itemCharacter>();
                JSONArray jsonCharacters = new JSONArray(result);
                itemCharacter item;
                for(int i=0;i<jsonCharacters.length();i++){
                    item=new itemCharacter();
                    item.set_id(jsonCharacters.getJSONObject(i).get("_id").toString());
                    item.setTag(jsonCharacters.getJSONObject(i).get("tag").toString());
                    //JSONArray jsonCharactersDetail = new JSONArray(jsonCharacters.getJSONObject(i).get("Detail"));
                    item.setThumbnail(jsonCharacters.getJSONObject(i).get("thumbnail").toString());
                    item.setName(jsonCharacters.getJSONObject(i).get("name").toString());
                    characters.add(item);
                }
                llm = new LinearLayoutManager(getActivity());
                rv.setLayoutManager(llm);
                adapter = new adapterCardViewCharacter(getActivity(),characters);
                rv.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
            }

        }
    }
}
