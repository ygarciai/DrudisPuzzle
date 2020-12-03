package com.example.drudispuzzle;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectionActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectionActivity  extends AppCompatActivity implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static boolean reproduciendo=false;
    public static MediaPlayer mp = null;
    private ContentValues values;
    Uri SoundUri;
    AudioManager audioManager,mAudioManager;
    AudioManager.OnAudioFocusChangeListener audioListener;
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener;
    private AudioFocusChangeListenerImpl mAudioFocusChangeListener;
    private boolean mFocusGranted, mFocusChanged;
    int result=0;


    public static boolean  encambioMusica=false;
    public static boolean  enBack=false;
    public SelectionActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectionActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectionActivity newInstance(String param1, String param2) {
        SelectionActivity fragment = new SelectionActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mp1=new MediaPlayer();
        mp = new MediaPlayer();
        mp = MediaPlayer.create(this, R.raw.jazzopedie);

        setContentView(R.layout.fragment_selection_activity);
        Button btnPlay = (Button) findViewById(R.id.button_inicioPartida);
        Button btnRank = (Button) findViewById(R.id.button_ranking);
        Button btnMult = (Button) findViewById(R.id.button_Multiplayer);
        Button btnFoto = (Button) findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button2);
        Button sonidoOn = (Button) findViewById(R.id.sonidoEncendido);
        Button sonidoOff = (Button) findViewById(R.id.sonidoApagado);
        Button seleccionMusica = (Button) findViewById(R.id.button_seleccionMusica);

        btnPlay.setOnClickListener(this);
        btnRank.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnFoto.setOnClickListener(this);
        btn3.setOnClickListener(this);
        sonidoOn.setOnClickListener(this);
        sonidoOff.setOnClickListener(this);
        seleccionMusica.setOnClickListener(this);
        if (!reproduciendo) {
            mp.setLooping(true);
            mp.start();
            reproduciendo = true;
        }
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
                    mp.start();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    mp.pause();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    mp.pause();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mp.pause();
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
                        mp.pause();
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        mp.start();
                        break;
                    default:
                }
            } catch (Exception e) {
            }
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selection_activity, container, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_inicioPartida:
                Intent intent = new Intent(view.getContext(), SeleccionTipoPuzzle.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.button_ranking:
                Intent intent1 = new Intent(view.getContext(), Ranking.class);
                startActivityForResult(intent1, 0);
                break;
            case R.id.button_Multiplayer:
                Intent intent2 = new Intent(view.getContext(), Multiplayer.class);
                startActivityForResult(intent2, 0);
                break;
            case R.id.button2:
                Intent intent3 = new Intent(view.getContext(), TomarFoto.class);
                startActivityForResult(intent3, 0);
                break;
            case R.id.sonidoEncendido: //comienza la reproduccion
                if(!reproduciendo) {//verifica que ya no se este reproduciendo
                    mp.setLooping(true);
                    mp.start();
                    reproduciendo=true;
                }
                break;
            case R.id.sonidoApagado:    //detiene la reproduccion
                if(reproduciendo) {
                    mp.pause();
                    mp.setLooping(false);
                    reproduciendo=false;
                }
                break;
            case R.id.button_seleccionMusica:
                encambioMusica=true;
                Intent intent5 = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent5, 10);
                reproduciendo=true;
                encambioMusica=true;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == 10) {
            Uri uriSound = data.getData();
            play(this, uriSound);
        }else{
            reproduciendo=false;
        }
    }

    private void play(Context context, Uri uri) {
        //añado
        Intent i = new Intent(this, MyServiceMusica.class);
        startService(i);
        //fin

        try {
            mp.stop();
            mp = new MediaPlayer();
            mp.setDataSource(context, uri);
            mp.prepare();
            mp.setLooping(true);
            mp.start();
            reproduciendo=true;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}