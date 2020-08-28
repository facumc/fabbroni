package com.acal.fabbroni;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private WebView mAcalWebView;
    private ProgressBar mProgressBar;
    private String url = "https://distribucionesmyd.com.ar/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUI();
        activarJavascript();
        mAcalWebView.loadUrl(url);
        // Forzamos el webview para que abra los enlaces internos dentro de la la APP
        mAcalWebView.setWebViewClient(new WebViewClient(){
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mProgressBar.setVisibility(View.VISIBLE);
                MainActivity.this.mProgressBar.setProgress(0);

                super.onPageStarted(view, url, favicon);
            }
        });

        // Add setWebChromeClient on WebView.
        mAcalWebView.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView View, int newProgress) {


                MainActivity.this.mProgressBar.setProgress(newProgress * 1000);
                mProgressBar.setProgress(newProgress);

                if (newProgress == 100) {
                    // Page loading finish
                    mProgressBar.setVisibility(View.GONE);

                }
            }
        });
    }

    // Inicializamos los elementos de la vista
    private void setUI() {
        mAcalWebView = (WebView) findViewById(R.id.AcalwebView);
        mProgressBar = (ProgressBar) findViewById(R.id.pb);
    }

    // Activamos javascript
    @SuppressLint("SetJavaScriptEnabled")
    private void activarJavascript() {
        WebSettings webSettings = mAcalWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    //Impedir que el botón Atrás cierre la aplicación
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (mAcalWebView.canGoBack()) {
                    mAcalWebView.goBack();
                } else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}