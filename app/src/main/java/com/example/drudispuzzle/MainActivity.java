package com.example.drudispuzzle;

import android.content.Context;
import android.content.Intent;
import android.content.res.loader.AssetsProvider;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText nombre;
    WebView webView;
    ApplicationLifecycleHandler controleventos;

    AudioManager audioManager,mAudioManager;
    AudioManager.OnAudioFocusChangeListener audioListener;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;
    private AudioFocusChangeListenerImpl mAudioFocusChangeListener;
    private boolean mFocusGranted, mFocusChanged;
    int result=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.botoninicio);
        Button btn2= findViewById(R.id.salir);
        controleventos=new ApplicationLifecycleHandler();

        controleventos.onActivityStarted(this);



        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish();
        }

        // añadimos la base de datos SQLite
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this, "bd player", null, 1);

        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mAudioFocusChangeListener = new AudioFocusChangeListenerImpl();

        result = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        switch (result) {
            case AudioManager.AUDIOFOCUS_REQUEST_GRANTED:
                mFocusGranted = true;
                break;
            case AudioManager.AUDIOFOCUS_REQUEST_FAILED:
                mFocusGranted = false;
                break;
        }

        ApplicationLifecycleHandler handler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(handler);
        registerComponentCallbacks(handler);



    }

    private class AudioFocusChangeListenerImpl implements AudioManager.OnAudioFocusChangeListener {
        @Override
        public void onAudioFocusChange(int focusChange) {
            mFocusChanged = true;
            //Log.i(TAG, "Focus changed");

            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    //mp.start();
                    ApplicationLifecycleHandler.mp1.start();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    //mp.pause();
                    ApplicationLifecycleHandler.mp1.pause();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    //mp.pause();
                    ApplicationLifecycleHandler.mp1.pause();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    //mp.pause();
                    ApplicationLifecycleHandler.mp1.pause();
                    break;
            }
        }
    }

    private final PhoneStateListener mPhoneListener=new PhoneStateListener(){
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            // Call receive state
            try {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        //mp.pause();
                        ApplicationLifecycleHandler.mp1.pause();
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        //mp.start();
                        ApplicationLifecycleHandler.mp1.start();
                        break;
                    default:
                }
            } catch (Exception e) {
            }
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_wv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "Opening Help", Toast.LENGTH_SHORT).show();
                setContentView(R.layout.webview);

                webView = (WebView) this.findViewById(R.id.webviewhtml);
                webView.setInitialScale(1);
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.getSettings().setUseWideViewPort(true);
                webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
                webView.setScrollbarFadingEnabled(false);
                webView.loadUrl("file:///android_asset/webview/index.html");

                return true;
            case R.id.Item2:
                Toast.makeText(this, "Goodbye", Toast.LENGTH_SHORT).show();
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.salir:
                finish();
                break;
            case R.id.botoninicio:
                Intent intent = new Intent(view.getContext(), SelectionActivity.class);
                startActivityForResult(intent, 0);
                break;
        }

    }
}
