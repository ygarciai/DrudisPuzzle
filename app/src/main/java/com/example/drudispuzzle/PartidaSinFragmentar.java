package com.example.drudispuzzle;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;


import java.util.ArrayList;

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

    int Nivel;
    int NumeroPiezas;

    ArrayList<Bitmap>Piezas;

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

        ArrayList<Uri> listaImagenes = (ArrayList<Uri>) getIntent().getSerializableExtra("imagenesSeleccionadasUri");

        final Chronometer myChronometer = (Chronometer)findViewById(R.id.chronometer);
        myChronometer.start();
        i1=(ImageView) findViewById(R.id.imageView_fondoPantalla);
        i1.setImageURI(listaImagenes.get(0));
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