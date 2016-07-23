package com.comics.gio.comics;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by giova on 22/07/2016.
 */
public class fragmentCharacterHome extends Fragment {
    View rootView;
    String urlImagen="";
    String description_="";
    fragmentCharacterHome(){

    }
    fragmentCharacterHome(String url, String description){
        urlImagen=url;
        description_=description;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_character_home, container, false);
        ImageView img= (ImageView) rootView.findViewById(R.id.imageCharacterHome);
        Picasso.with(getActivity()).load(urlImagen).into(img);
        if(!description_.equalsIgnoreCase("")){
            TextView tvCharacterDescription = (TextView)rootView.findViewById(R.id.tvDescriptionCharacter);
            tvCharacterDescription.setText(description_);
        }else{
            rootView.findViewById(R.id.tvDescriptionCharacter).setVisibility(View.GONE);
        }


        return  rootView;
    }
}
