package com.example.drudispuzzle;

import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartidaSinFragmentar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartidaSinFragmentar extends AppCompatActivity implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageView i1;
    ImageView i2;
    ImageView i3;
    ImageView i4;
    ImageView i5;

    public PartidaSinFragmentar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PartidaSinFragmentar.
     */
    // TODO: Rename and change types and number of parameters
    public static PartidaSinFragmentar newInstance(String param1, String param2) {
        PartidaSinFragmentar fragment = new PartidaSinFragmentar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_partida_sin_fragmentar);

        final Chronometer myChronometer = (Chronometer)findViewById(R.id.chronometer);
        myChronometer.start();

        i1= (ImageView) findViewById(R.id.imagenSeleccionada);
        i2= (ImageView) findViewById(R.id.imagenSeleccionada1);
        i3= (ImageView) findViewById(R.id.imagenSeleccionada2);
        i4= (ImageView) findViewById(R.id.imagenSeleccionada3);
        i5= (ImageView) findViewById(R.id.imagenSeleccionada4);

        Bundle bundle= getIntent().getExtras();
        if (bundle!=null){
            int resid= bundle.getInt("resId");
            i1.setImageResource(resid);
            i2.setImageResource(resid);
            i3.setImageResource(resid);
            i4.setImageResource(resid);
            i5.setImageResource(resid);
        }
        
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_partida_sin_fragmentar, container, false);
    }

    @Override
    public void onClick(View v) {

    }
}