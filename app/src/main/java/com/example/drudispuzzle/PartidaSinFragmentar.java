package com.example.drudispuzzle;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import static java.lang.StrictMath.abs;


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

    ImageView i1;
    ImageView i0;
    int nivel;
    Bitmap bitmapCuboBlanco;

    public static List<Bitmap> piezas;
    public static List<Bitmap> piezasCubo;
    int numberPieces = 24;
    int rows, cols;
    int chunkHeight,chunkWidth;

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
        nivel=1;

        ArrayList<Uri> listaImagenes = (ArrayList<Uri>) getIntent().getSerializableExtra("imagenesSeleccionadasUri");
        final Chronometer myChronometer = findViewById(R.id.chronometer);
        myChronometer.start();
        i1 = findViewById(R.id.imageView_fondoPantalla);
        inicioPartida(nivel, listaImagenes);
   }

    private void inicioPartida(int nivel, ArrayList<Uri> listaImagenes) {
        GridLayout layout = (GridLayout) findViewById(R.id.secondLinearLayout);
        GridLayout layout2 = (GridLayout) findViewById(R.id.thirdLinearLayout);

        if (nivel == 1){
            i1.setImageURI(listaImagenes.get(0));

            splitImage(i1, numberPieces);
            //crearCubo(bm, numberPieces);
            layout.setColumnCount(cols);
            layout2.setColumnCount(cols);

            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.fondoblanco);
            Bitmap bmCopy = bm.copy(Bitmap.Config.ARGB_8888,true);

            bmCopy.setWidth(chunkWidth);
            bmCopy.setHeight(chunkHeight);

            for(Bitmap piece : piezas) {
                ImageView iv = new ImageView(getApplicationContext());
                ImageView cuboVacio = new ImageView(getApplicationContext());
                iv.setImageBitmap(piece);
                cuboVacio.setImageBitmap(bmCopy);
                layout.addView(iv);
                layout2.addView(cuboVacio);
            }
        }else if (nivel==2){
            i1.setImageURI(listaImagenes.get(1));
        }else if (nivel==3){
            i1.setImageURI(listaImagenes.get(2));
        }else if (nivel==4){
            i1.setImageURI(listaImagenes.get(3));
        }else if (nivel==5){
            i1.setImageURI(listaImagenes.get(4));
        }

    }

    private void splitImage(ImageView image, int chunkNumbers) {
        piezas = new ArrayList<>(chunkNumbers);

        //Getting the scaled bitmap of the source image
        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        //rows = cols = (int) Math.sqrt(chunkNumbers);
        rows = 4;
        cols = 6;
        chunkHeight = bitmap.getHeight()/rows;
        chunkWidth = bitmap.getWidth()/cols;

        int yCoord = 0;
        for(int x=0; x<rows; x++){
            int xCoord = 0;
            for(int y=0; y<cols; y++){
                piezas.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }

        Collections.shuffle(piezas);

    }


    @Override
    public void onClick(View v) {

    }
}