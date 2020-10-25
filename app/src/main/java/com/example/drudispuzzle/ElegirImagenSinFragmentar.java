package com.example.drudispuzzle;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ElegirImagenSinFragmentar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ElegirImagenSinFragmentar extends AppCompatActivity implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ImageView imagen1;
    Boolean image1Set;
    ImageView imagen2;
    Boolean image2Set;
    ImageView imagen3;
    Boolean image3Set;
    ImageView imagen4;
    Boolean image4Set;
    ImageView imagen5;
    Boolean image5Set;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ElegirImagenSinFragmentar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ElegirImagenSinFragmentar.
     */
    // TODO: Rename and change types and number of parameters
    public static ElegirImagenSinFragmentar newInstance(String param1, String param2) {
        ElegirImagenSinFragmentar fragment = new ElegirImagenSinFragmentar();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_elegir_imagen_sin_fragmentar);
        Button buttonImagen = (Button) findViewById(R.id.button_SeleccionarImagen);
        Button buttonEmpezar = (Button) findViewById(R.id.button_empezarPartidaSinFragmentar);
        buttonImagen.setOnClickListener(this);
        buttonEmpezar.setOnClickListener(this);
        buttonEmpezar.setEnabled(false);
        imagen1= (ImageView) findViewById(R.id.imagenSeleccionada);
        image1Set = false;
        imagen2= (ImageView) findViewById(R.id.imagenSeleccionada1);
        image2Set = false;
        imagen3= (ImageView) findViewById(R.id.imagenSeleccionada2);
        image3Set = false;
        imagen4= (ImageView) findViewById(R.id.imagenSeleccionada3);
        image4Set = false;
        imagen5= (ImageView) findViewById(R.id.imagenSeleccionada4);
        image5Set = false;

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elegir_imagen_sin_fragmentar, container, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_SeleccionarImagen:
                cargarimagen();
                break;
        }

    }

    private void cargarimagen() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccion la Aplicacion"),10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && !image1Set){
            Uri path=data.getData();
            imagen1.setImageURI(path);
            image1Set = true;
        } else if (resultCode==RESULT_OK && !image2Set){
            Uri path=data.getData();
            imagen2.setImageURI(path);
            image2Set = true;
        }else if (resultCode==RESULT_OK && !image3Set){
            Uri path=data.getData();
            imagen3.setImageURI(path);
            image3Set = true;
        }else if (resultCode==RESULT_OK && !image4Set){
            Uri path=data.getData();
            imagen4.setImageURI(path);
            image4Set = true;
        }else if (resultCode==RESULT_OK && !image5Set){
            Uri path=data.getData();
            imagen5.setImageURI(path);
            image5Set = true;
            Button buttonImagen2 = (Button) findViewById(R.id.button_SeleccionarImagen);
            buttonImagen2.setEnabled(false);
            Button buttonEmpezar = (Button) findViewById(R.id.button_empezarPartidaSinFragmentar);
            buttonEmpezar.setEnabled(true);
        }
    }
    private void cargarimagen1() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccion la Aplicacion"),10);
    }

}