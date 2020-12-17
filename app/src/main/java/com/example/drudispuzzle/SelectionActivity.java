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

import butterknife.BindView;

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
    static boolean reproduciendo=false;
    public static boolean  encambioMusica=false;

    //@BindView(R.id.button_inicioPartida) Button btnPlay;
    //@BindView(R.id.button_ranking) Button btnRank;
    //@BindView(R.id.button_Multiplayer) Button btnMult;
    //@BindView(R.id.button2) Button btnFoto;
    //@BindView(R.id.button2) Button btn3;
    //@BindView(R.id.sonidoEncendido) Button sonidoOn;
    //@BindView(R.id.sonidoApagado) Button sonidoOff;
    //@BindView(R.id.button_seleccionMusica) Button seleccionMusica;

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

        setContentView(R.layout.fragment_selection_activity);
        Button btnPlay = (Button) findViewById(R.id.button_inicioPartida);
        Button btnRank = (Button) findViewById(R.id.button_ranking);
        Button btnMult = (Button) findViewById(R.id.button_Multiplayer);
        Button btnFoto = (Button) findViewById(R.id.button2);
        Button btn4 = findViewById(R.id.button2);
        Button sonidoOn = (Button) findViewById(R.id.sonidoEncendido);
        Button sonidoOff = (Button) findViewById(R.id.sonidoApagado);
        Button seleccionMusica = (Button) findViewById(R.id.button_seleccionMusica);

        btnPlay.setOnClickListener(this);
        btnRank.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnFoto.setOnClickListener(this);
        btn4.setOnClickListener(this);
        sonidoOn.setOnClickListener(this);
        sonidoOff.setOnClickListener(this);
        seleccionMusica.setOnClickListener(this);

        ApplicationLifecycleHandler handler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(handler);
        registerComponentCallbacks(handler);

        }

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
                //startActivityForResult(intent3, 0);
                break;
            case R.id.sonidoEncendido: //comienza la reproduccion
                if(!ApplicationLifecycleHandler.repro) {//verifica que ya no se este reproduciendo
                    ApplicationLifecycleHandler.mp1.setLooping(true);
                    ApplicationLifecycleHandler.mp1.start();
                    ApplicationLifecycleHandler.repro=true;
                }
                break;
            case R.id.sonidoApagado:    //detiene la reproduccion
                if(ApplicationLifecycleHandler.repro) {
                    ApplicationLifecycleHandler.mp1.pause();
                    ApplicationLifecycleHandler.mp1.setLooping(false);
                    ApplicationLifecycleHandler.repro=false;
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
        //startService(i);
        //fin

        try {
            ApplicationLifecycleHandler.mp1.stop();
            ApplicationLifecycleHandler.mp1 = new MediaPlayer();
            ApplicationLifecycleHandler.mp1.setDataSource(context, uri);
            ApplicationLifecycleHandler.mp1.prepare();
            ApplicationLifecycleHandler.mp1.setLooping(true);
            ApplicationLifecycleHandler.mp1.start();
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