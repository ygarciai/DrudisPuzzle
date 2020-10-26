package com.example.drudispuzzle;

import android.content.Intent;
import android.graphics.Bitmap;
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

import java.lang.reflect.Array;
import java.util.ArrayList;

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
    ArrayList<ImageView> imagenesSeleccionadas;
    ArrayList<Bitmap> imagenesSeleccionadasBitmap;
    ArrayList<Uri> imagenesSeleccionadasUri;

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
        imagenesSeleccionadas = new ArrayList<ImageView>();
        imagenesSeleccionadasBitmap = new ArrayList<Bitmap>();
        imagenesSeleccionadasUri = new ArrayList<Uri>();
        Button buttonImagen = (Button) findViewById(R.id.button_SeleccionarImagen);
        Button buttonEmpezar = (Button) findViewById(R.id.button_empezarPartidaSinFragmentar);
        buttonImagen.setOnClickListener(this);
        buttonEmpezar.setOnClickListener(this);
        buttonEmpezar.setEnabled(false);
        imagen1= (ImageView) findViewById(R.id.imagenSeleccionada);
        imagenesSeleccionadas.add(imagen1);


        image1Set = false;
        imagen2= (ImageView) findViewById(R.id.imagenSeleccionada1);
        image2Set = false;
        imagenesSeleccionadas.add(imagen2);



        imagen3= (ImageView) findViewById(R.id.imagenSeleccionada2);
        image3Set = false;
        imagenesSeleccionadas.add(imagen3);



        imagen4= (ImageView) findViewById(R.id.imagenSeleccionada3);
        image4Set = false;
        imagenesSeleccionadas.add(imagen4);




        imagen5= (ImageView) findViewById(R.id.imagenSeleccionada4);
        image5Set = false;
        imagenesSeleccionadas.add(imagen5);


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
            case R.id.button_empezarPartidaSinFragmentar:
                Intent intent = new Intent(view.getContext(), PartidaSinFragmentar.class);
                intent.putExtra("imagenesSeleccionadasUri", imagenesSeleccionadasUri);
                startActivityForResult(intent, 0);
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
            BitmapDrawable drawable = (BitmapDrawable) imagen1.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            imagenesSeleccionadasBitmap.add(bitmap);
            imagenesSeleccionadasUri.add(path);
        } else if (resultCode==RESULT_OK && !image2Set){
            Uri path=data.getData();
            imagen2.setImageURI(path);
            image2Set = true;
            BitmapDrawable drawable1 = (BitmapDrawable) imagen2.getDrawable();
            Bitmap bitmap1 = drawable1.getBitmap();
            imagenesSeleccionadasBitmap.add(bitmap1);
            imagenesSeleccionadasUri.add(path);
        }else if (resultCode==RESULT_OK && !image3Set){
            Uri path=data.getData();
            imagen3.setImageURI(path);
            image3Set = true;
            BitmapDrawable drawable2 = (BitmapDrawable) imagen3.getDrawable();
            Bitmap bitmap2 = drawable2.getBitmap();
            imagenesSeleccionadasBitmap.add(bitmap2);
            imagenesSeleccionadasUri.add(path);
        }else if (resultCode==RESULT_OK && !image4Set){
            Uri path=data.getData();
            imagen4.setImageURI(path);
            image4Set = true;
            BitmapDrawable drawable3 = (BitmapDrawable) imagen4.getDrawable();
            Bitmap bitmap3 = drawable3.getBitmap();
            imagenesSeleccionadasBitmap.add(bitmap3);
            imagenesSeleccionadasUri.add(path);
        }else if (resultCode==RESULT_OK && !image5Set){
            Uri path=data.getData();
            imagen5.setImageURI(path);
            image5Set = true;
            Button buttonImagen2 = (Button) findViewById(R.id.button_SeleccionarImagen);
            buttonImagen2.setEnabled(false);
            Button buttonEmpezar = (Button) findViewById(R.id.button_empezarPartidaSinFragmentar);
            buttonEmpezar.setEnabled(true);        BitmapDrawable drawable4 = (BitmapDrawable) imagen5.getDrawable();
            Bitmap bitmap4 = drawable4.getBitmap();
            imagenesSeleccionadasBitmap.add(bitmap4);
            imagenesSeleccionadasUri.add(path);

        }
    }
    private void cargarimagen1() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccion la Aplicacion"),10);
    }

}