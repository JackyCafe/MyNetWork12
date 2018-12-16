package com.linyanheng.mynetwork1;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    WebView webview;
    StringBuffer sb;
    UIHandler uiHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webview = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        sb = new StringBuffer() ;
        uiHandler = new UIHandler();

    }

    public void test1(View view) {

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL("https://tw.yahoo.com");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String lines ;
                    while((lines = reader.readLine())!=null )
                    {
                         sb.append(lines+"\n");
                    }
                    reader.close();
                    uiHandler.sendEmptyMessage(0);
                } catch (Exception e) {
                    Log.v("YH",e.toString());
                }

            }
        }
                .start();

    }
    private class UIHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                webview.loadData(sb.toString(),"text/html","utf-8");
                break;
            }
        }
    }

 }
