/**
 * On the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * (c) 2020 grzegorz.bylica@gmail.com
 */

package org.gb.gate_keeper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.http.SslError;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import org.gb.gate_keeper.R;

import java.io.IOException;

/**
 * @author grzegorz.bylica@gmail.com
 */
public class GK extends AppCompatActivity {

    private WebView mWebview;

    private Config conf;

    private void alert(String msg) {
        AlertAct al = new AlertAct(msg);
        al.show(getSupportFragmentManager(), "Msg");
    }

    private final void reloadPage() {
        try {
            mWebview = (WebView) findViewById(R.id.webView);
            mWebview.loadUrl(conf.url);
        } catch (Exception e) {
            Log.e("", e.getMessage());
            alert("Url:" + conf.url + " Not loaded:" + e.getMessage());
        }
    }

    private final void beep(String fn) {
        try {
            MediaPlayer mp;
            AssetFileDescriptor afd = this.getAssets().openFd(fn);
            mp = new MediaPlayer();
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mp.prepare();
            mp.start();
        } catch (Exception e) {
            Log.e("", e.getMessage());
            alert("Sound:" + fn + " No play:" + e.getMessage());
        }
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            conf = new Config(getFilesDir(), getApplicationContext());
        } catch (IOException e) {
            Log.e("", e.getMessage());
            alert(e.getMessage());
        } catch (Exception e) {
            Log.e("", e.getMessage());
            alert(e.getMessage());
        }

        setTitle(conf.name);
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(intent.getAction())) {
                Log.e("", "Main Activity is not the root.  Finishing Main Activity instead of launching.");
                finish();
                return;
            }
        }

        AudioManager am =
                (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        am.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                am.getStreamMaxVolume(AudioManager.STREAM_MUSIC),
                0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Label label = findViewById(R.id.label);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton btn = findViewById(R.id.imageButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reloadPage();
            }
        });

        try {
            mWebview = (WebView) findViewById(R.id.webView);
            mWebview.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                    if (message.contains(".ogg")) {
                        beep(message.trim());
                    }
                    super.onConsoleMessage(message, lineNumber, sourceID);
                }
            });
            mWebview.getSettings().setJavaScriptEnabled(true);
            final Activity activity = this;
            mWebview.setWebViewClient(new WebViewClient() {

                @SuppressWarnings("deprecation")
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    alert(description);
                }

                @TargetApi(android.os.Build.VERSION_CODES.M)
                @Override
                public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                    onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    handler.proceed();
                }

            });

            mWebview.setWebContentsDebuggingEnabled(true);
            String newUA = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
            mWebview.getSettings().setUserAgentString(newUA);
            mWebview.getSettings().setJavaScriptEnabled(true);
            mWebview.getSettings().setLoadWithOverviewMode(true);
            mWebview.getSettings().setUseWideViewPort(true);
            mWebview.getSettings().setAllowContentAccess(true);
            mWebview.getSettings().setDomStorageEnabled(true);
            mWebview.getSettings().setMediaPlaybackRequiresUserGesture(false);
            mWebview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            mWebview.clearCache(true);
            mWebview.getSettings().setAllowContentAccess(true);

            mWebview.requestFocus();
            mWebview.loadUrl(conf.url);
        } catch (Exception e) {
            Log.e("", e.getMessage());
            alert(e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_change_pin) {
            CheckPIN checkPIN = new CheckPIN(conf, new MyAction() {
                @Override
                public void make() {
                    ChangePINAct changePINAct = new ChangePINAct(
                            new MyActionStr() {
                                @Override
                                public void make(String data) {
                                    try {
                                        conf.setNewPin(data);
                                    } catch (Exception e) {
                                        alert(e.getMessage());
                                    }
                                }
                            }
                    );
                    changePINAct.show(getSupportFragmentManager(), "PIN");
                }
            }, "PIN is changed");
            checkPIN.show(getSupportFragmentManager(), "PIN");
        } else if (id == R.id.action_change_url) {
            CheckPIN checkPIN = new CheckPIN(conf, new MyAction() {
                @Override
                public void make() {
                    ChangeURLAct changeURLAct = new ChangeURLAct(
                            new MyActionStr() {
                                @Override
                                public void make(String data) {
                                    alert("test");
                                }
                            },
                            conf
                    );
                    changeURLAct.show(getSupportFragmentManager(), "URL");
                }
            }, "URL is changed");
            checkPIN.show(getSupportFragmentManager(), "PIN");
        } else if (id == R.id.action_stop) {
            CheckPIN checkPIN = new CheckPIN(conf, new MyAction() {
                @Override
                public void make() {
                    try {
                        System.exit(0);
                    } catch (Exception e) {
                        alert(e.getMessage());
                    }
                }
            }, "Stop");
            checkPIN.show(getSupportFragmentManager(), "PIN");
        } else if (id == R.id.action_change_name) {
            CheckPIN checkPIN = new CheckPIN(conf, new MyAction() {
                @Override
                public void make() {
                    //alert("test moj");
                    ChangeNameAct changeNameAct = new ChangeNameAct(
                            new MyActionStr() {
                                @Override
                                public void make(String data) {
                                    try {
                                        conf.setNewName(data);
                                        setTitle(conf.name);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, conf
                    );

                    changeNameAct.show(getSupportFragmentManager(), "Name");
                }
            }, "Changed name");
            checkPIN.show(getSupportFragmentManager(), "PIN");
        };
        return super.onOptionsItemSelected(item);
    }
}