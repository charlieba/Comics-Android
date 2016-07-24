package com.comics.gio.comics;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

/**
 * Created by giova on 22/07/2016.
 */
public class fragmentCharacterHome extends Fragment {
    View rootView;
    String urlImagen="";
    String description_="";
    ImageButton shareButton;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    String name_="";
    String wiki_="";
    String detail_="";
    fragmentCharacterHome(){

    }
    fragmentCharacterHome(String url, String description, String name, String wiki, String detail){
        urlImagen=url;
        description_=description;
        wiki_=wiki;
        name_=name;
        detail_=detail;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_character_home, container, false);
        callbackManager = CallbackManager.Factory.create();
        ImageView img= (ImageView) rootView.findViewById(R.id.imageCharacterHome);
        Picasso.with(getActivity()).load(urlImagen).into(img);
        if(!description_.equalsIgnoreCase("")){
            TextView tvCharacterDescription = (TextView)rootView.findViewById(R.id.tvDescriptionCharacter);
            tvCharacterDescription.setText(description_);
        }else{
            rootView.findViewById(R.id.tvDescriptionCharacter).setVisibility(View.GONE);
        }

        shareButton = (ImageButton) rootView.findViewById(R.id.share_btn);
        shareDialog = new ShareDialog(this);
        shareButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent;
                    if(wiki_!=""){
                        linkContent= new ShareLinkContent.Builder()
                                .setContentTitle(name_)
                                .setContentDescription(
                                        description_)
                                .setContentUrl(Uri.parse(wiki_))
                                .build();
                    }else{
                        linkContent = new ShareLinkContent.Builder()
                                .setContentTitle(name_)
                                .setContentDescription(
                                        description_)
                                .setContentUrl(Uri.parse(detail_))
                                .build();
                    }


                    shareDialog.show(linkContent);
                }
            }
        });




        return  rootView;
    }
    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
