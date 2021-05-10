package org.gb.gate_keeper;

import android.app.Activity;
import android.os.Bundle;

import org.gb.gate_keeper.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity {

    private WebView mWebview ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ImageButton btn = findViewById(R.id.imageButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mWebview = (WebView) findViewById(R.id.webView);
                    Config conf = new Config(getFilesDir(), getApplicationContext());
                    mWebview.loadUrl(conf.url);
                } catch (Exception e) {
                    Log.e("",e.getMessage());
                }
            }
        });

        try {
            Log.v("", "create");
            mWebview = (WebView) findViewById(R.id.webView);
            mWebview.getSettings().setJavaScriptEnabled(true);
            final Activity activity = this;
                        mWebview.setWebViewClient(new WebViewClient() {

                //@TargetApi(android.os.Build.VERSION_CODES.M)
                @Override
                public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                    Log.v("", "test1");
                    // Redirect to deprecated method, so you can use it in all SDK versions
                    Toast.makeText(activity, "test2", Toast.LENGTH_SHORT).show();
                    //onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                }
            });


            String newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
            mWebview.getSettings().setUserAgentString(newUA);
            mWebview.getSettings().setJavaScriptEnabled(true);
            mWebview.getSettings().setLoadWithOverviewMode(true);
            mWebview.getSettings().setUseWideViewPort(true);
            mWebview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            mWebview.clearCache(true);

            Config conf = new Config(getFilesDir(), getApplicationContext());
            mWebview.loadUrl(conf.url);
        } catch (Exception e) {
            Log.v("",e.getMessage());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

}