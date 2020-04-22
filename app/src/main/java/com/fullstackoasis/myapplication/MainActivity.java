package com.fullstackoasis.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getCanonicalName();
    private static String URL = "https://accounts.google.com/signup/v2/webcreateaccount?flowName=GlifWebSignIn&flowEntry=SignUp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context applicationContext = this.getApplicationContext();
        final WebView myWebView = findViewById(R.id.myWebView);
        // setContentView(myWebView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (URL.equals(url)) {
                    // Only show keyboard on search page entry
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.restartInput(view);
                    view.loadUrl(focusInputAPI23());
                    // This opens up the keyboard, but focus is not on TextInput unless
                    // you load the JavaScript above, first.
                    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });
        myWebView.loadUrl(URL);

    }

    /**
     * See https://medium.com/@filipe.batista/inject-javascript-into-webview-2b702a2a029f
     * This removes the image in the Google search page. It works in API 23.
     * If there's no image, it removes the big Google text.
     * @return
     */
    private String removeHpLogo() {
        return "javascript:(function() { " +
                "var element = document.getElementById('hplogo');"
                + "element.parentNode.removeChild(element);" +
                "})()";
    }

    /**
     * Example where the background of the web page is set to have red color.
     * It works in API 23.
     * @return String JavaScript function which sets background to red.
     */
    private String colorRed() {
        return "javascript:(function() { " +
                "document.body.style.background='red';" +
                "})()";
    }

    /**
     * DOES work in the Emulator  API 23.
     * @return
     */
    private String focusInputAPI23() {
        return "javascript:document.getElementsByName('q')[0].focus();";
    }

    /**
     * Does NOT seem to work in the Emulator API 23.
     * @return
     */
    private String focusInput() {
        return "javascript:(function() { " +
                "let els = document.getElementsByName('q');" +
                "els[0].focus();" +
                "})()";
    }

    /**
     * Does NOT seem to work in the Emulator API 23.
     * @return
     */
    private String clickInput() {
        String str = "javascript:(function() {"+
                "let els = document.getElementsByName('q');" +
                "let el = els[0]; " +
                "let e = document.createEvent('HTMLEvents');" +
                "e.initEvent('click',true,true);" +
                "el.dispatchEvent(e);" +
                "})()";
        Log.d(TAG, str);
        return str;
    }

}
