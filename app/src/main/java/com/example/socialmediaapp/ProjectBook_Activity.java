package com.example.socialmediaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class ProjectBook_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_book_);
        setUpInternetPage("https://pdfhost.io/v/X4sYJXafd__SocialMediaApp_");
    }

    public void setUpInternetPage(String url){
        WebView webView = findViewById(R.id.webView);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}