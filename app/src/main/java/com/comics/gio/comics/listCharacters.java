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
import android.widget.RelativeLayout;

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
    int lastFirstVisiblePosition;
    int ScrollY=0;
    View rootView;
    int page=1;
    Boolean scroll=false;
    int limit=20;
    int skip=0;
    RelativeLayout loading;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_list_characters, container, false);
        //rootView.setTag(TAG);
        characters=new ArrayList<itemCharacter>();
        rv = (RecyclerView) rootView.findViewById(R.id.rvCharacter);
        loading=(RelativeLayout) rootView.findViewById(R.id.loadingPanel);

        String searchQuery = "";
        Bundle bundle=getArguments();

        if(bundle !=null){
            if(bundle.containsKey("searchQuery")){
                searchQuery=getArguments().getString("searchQuery");
            }
        }
        if(searchQuery!="" && searchQuery!=null){
            String[] params={"http://www.comicscharacter.com/search?key="+searchQuery};
            new HttpAsyncTask().execute(params);
        }else{
            String[] params={"http://www.comicscharacter.com/get_character?limit="+limit+"&skip="+skip};
            new HttpAsyncTask().execute(params);
        }


        if(searchQuery.equalsIgnoreCase("")){
            rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                    if ((!recyclerView.canScrollVertically(1))&&(characters.size()!=0)) {

                        //System.out.println("Scroll: Llego al final");

                        lastFirstVisiblePosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                        page++;
                        ScrollY=dy;
                        //int ScrollX=dx;

                        //System.out.println("ScrollY "+ScrollY);
                        //view.findViewById(R.id.loadingPanelPaginacion).setVisibility(View.VISIBLE);
                        scroll=true;
                        skip=skip+limit;
                        String[] params={"http://www.comicscharacter.com/get_character?limit="+limit+"&skip="+skip};
                        new HttpAsyncTask().execute(params);
                        rootView.findViewById(R.id.loadingPanelPaginacion).setVisibility(View.VISIBLE);
                        //String[] params={Request + "&page=" + page,"resultados"};
                        //new HttpAsyncTask().execute(params);
                        //recyclerView.setScrollY(ScrollY);
                        //recyclerView.setScrollY(ScrollY);

                        //recyclerView.scrollTo(ScrollX,ScrollY);
                    } else if (dy < 0) {
                        // System.out.println("Scroll: Sube");
                    } else if (dy > 0) {
                        //System.out.println("Scroll: Baja");
                    }
                }
            });
        }





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
                JSONArray jsonCharacters = new JSONArray(result);
                itemCharacter item;
                for(int i=0;i<jsonCharacters.length();i++){
                    item=new itemCharacter();
                    item.set_id(jsonCharacters.getJSONObject(i).get("_id").toString());
                    item.setTag(jsonCharacters.getJSONObject(i).get("tag").toString());
                    JSONObject jsonDetail=new JSONObject();
                    JSONArray jsonCharactersDetail = new JSONArray(jsonCharacters.getJSONObject(i).get("detail").toString());
                        for(int j=0;j<jsonCharactersDetail.length();j++){

                            if(jsonCharactersDetail.getJSONObject(j).get("type").toString().equalsIgnoreCase("detail")){
                                item.setDetail(jsonCharactersDetail.getJSONObject(j).get("url").toString());
                            }else
                            if(jsonCharactersDetail.getJSONObject(j).get("type").toString().equalsIgnoreCase("wiki")){
                                item.setWiki(jsonCharactersDetail.getJSONObject(j).get("url").toString());
                            }else
                            if(jsonCharactersDetail.getJSONObject(j).get("type").toString().equalsIgnoreCase("comiclink")){
                                item.setComicLink(jsonCharactersDetail.getJSONObject(j).get("url").toString());
                            }
                        }
                    item.setThumbnail(jsonCharacters.getJSONObject(i).get("thumbnail").toString());
                    item.setName(jsonCharacters.getJSONObject(i).get("name").toString());
                    characters.add(item);
                }
                llm = new LinearLayoutManager(getActivity());
                rv.setLayoutManager(llm);
                if(getActivity()!=null){
                    adapter = new adapterCardViewCharacter(getActivity(),characters);
                    rv.setAdapter(adapter);
                }
                if(scroll){
                    ((LinearLayoutManager) rv.getLayoutManager()).scrollToPosition(lastFirstVisiblePosition);
                    scroll=false;
                }
                loading.setVisibility(View.GONE);
                rootView.findViewById(R.id.loadingPanelPaginacion).setVisibility(View.GONE);
            } catch (JSONException e) {
                e.printStackTrace();
                System.err.println(e.getMessage());
            }

        }
    }
}
