package com.comics.gio.comics;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.comics.gio.comics.R;
import com.comics.gio.comics.itemCharacter;

import java.util.ArrayList;

/**
 * Created by giova on 17/07/2016.
 */
public class fragmentWikiCharacter extends Fragment {
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_wiki_character, container, false);
        WebView myWebView = (WebView) rootView.findViewById(R.id.wVWikiCharacter);
        WebSettings webSettings = myWebView.getSettings();
        myWebView.setInitialScale(1);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        myWebView.setScrollbarFadingEnabled(false);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setDisplayZoomControls(false);
        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                rootView.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                rootView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }

        });
        myWebView.loadUrl("http://marvel.com/universe/3-D_Man_(Chandler)?utm_campaign=apiRef&utm_source=52b6305c2146fd0f86ae99c9878fcdc2");
        return  rootView;
    }
}
