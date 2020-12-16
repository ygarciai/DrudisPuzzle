package com.example.drudispuzzle;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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

    private static final String TAG = "TomarFoto";
    private ContentValues values;
    private Uri imageUri;
    private static final int PICTURE_RESULT = 122 ;
    private Bitmap thumbnail;
    String imageurl;
    boolean escamara=false;

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
    public ArrayList<Uri> imagenesSeleccionadasUri;

    private Button myButtonFoto;

    public StorageReference mStorageRef;
    public int i=0;
    public int contador=0;

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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ApplicationLifecycleHandler handler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(handler);
        registerComponentCallbacks(handler);

        imagenesSeleccionadas = new ArrayList<ImageView>();
        imagenesSeleccionadasBitmap = new ArrayList<Bitmap>();
        imagenesSeleccionadasUri = new ArrayList<Uri>();

        Button buttonImagen = (Button) findViewById(R.id.button_SeleccionarImagen);
        Button buttonEmpezar = (Button) findViewById(R.id.button_empezarPartidaSinFragmentar);
        buttonImagen.setOnClickListener(this);
        buttonEmpezar.setOnClickListener(this);


        myButtonFoto = (Button)findViewById(R.id.button_FotoCamara);
        myButtonFoto.setOnClickListener(this);

        buttonEmpezar.setEnabled(true);
        buttonImagen.setEnabled(false);
        myButtonFoto.setEnabled(false);

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

        mStorageRef = FirebaseStorage.getInstance().getReference();

        try {
            cargarimagenesFirebase();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void cargarimagenesFirebase() throws IOException {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("images");

        //int numero = (int) (Math.random() * 10) + 1;
        //String imagenaleatoria=String.valueOf(numero);
        //imagenaleatoria=imagenaleatoria+".jpg";
        //Log.d(TAG,imagenaleatoria);

        NumeroAleatorios na = new NumeroAleatorios(1,10);
        for(i = 0; i < 5;i++){
            String imagenaleatoria=String.valueOf(na.generar());
            imagenaleatoria=imagenaleatoria+".jpg";
            storageRef.child(imagenaleatoria).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    InputStream in=null;
                    //Log.d(TAG, "Encuentra URI");
                    String uriString;
                    uriString=uri.toString();
                    try {
                        in = new URL(uriString).openStream();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (contador==0){
                        Bitmap mIcon11 = BitmapFactory.decodeStream(in);
                        imagenesSeleccionadasBitmap.add(mIcon11);
                        imagenesSeleccionadasUri.add(uri);
                        imagen1.setImageBitmap(mIcon11);
                        Log.d(TAG, "Entra");
                        contador++;
                    }else if (contador==1){
                        Bitmap mIcon11 = BitmapFactory.decodeStream(in);
                        imagenesSeleccionadasBitmap.add(mIcon11);
                        imagenesSeleccionadasUri.add(uri);
                        imagen2.setImageBitmap(mIcon11);
                        Log.d(TAG, "Entra 1");
                        contador++;
                    }else if (contador==2){
                        Bitmap mIcon11 = BitmapFactory.decodeStream(in);
                        imagenesSeleccionadasBitmap.add(mIcon11);
                        imagenesSeleccionadasUri.add(uri);
                        imagen3.setImageBitmap(mIcon11);
                        Log.d(TAG, "Entra 1");
                        contador++;
                    }else if (contador==3){
                        Bitmap mIcon11 = BitmapFactory.decodeStream(in);
                        imagenesSeleccionadasBitmap.add(mIcon11);
                        imagenesSeleccionadasUri.add(uri);
                        imagen4.setImageBitmap(mIcon11);
                        Log.d(TAG, "Entra 1");
                        contador++;
                    }else if (contador==4){
                        Bitmap mIcon11 = BitmapFactory.decodeStream(in);
                        imagenesSeleccionadasBitmap.add(mIcon11);
                        imagenesSeleccionadasUri.add(uri);
                        imagen5.setImageBitmap(mIcon11);
                        Log.d(TAG, "Entra 1");
                        contador++;
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                    Log.d(TAG, "No Encuentra URI");
                }
            });
        }

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elegir_imagen_sin_fragmentar, container, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_SeleccionarImagen:
                cargarimagen();
                break;
            case R.id.button_empezarPartidaSinFragmentar:
                escamara=false;
                Intent intent = new Intent(view.getContext(), PartidaSinFragmentar.class);
                intent.putExtra("imagenesSeleccionadasUri", imagenesSeleccionadasUri);
                startActivityForResult(intent, 0);
                break;
            case R.id.button_FotoCamara:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //Check permissions for Android 6.0+
                    if (!checkExternalStoragePermission()) {
                        return;
                    }
                    escamara=true;
                    values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "Fotos");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "Foto sacada " + System.currentTimeMillis());
                    imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent2, PICTURE_RESULT);
                }

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
        if ((resultCode==RESULT_OK && !image1Set) || (escamara==true && !image1Set)){
            if (escamara == false) {
                Uri path=data.getData();
                imagen1.setImageURI(path);
                image1Set = true;
                BitmapDrawable drawable = (BitmapDrawable) imagen1.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                imagenesSeleccionadasBitmap.add(bitmap);
                imagenesSeleccionadasUri.add(path);
            }else{
                try {
                    thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    imagen1.setImageBitmap(thumbnail);
                    imageurl = getRealPathFromURI(imageUri);
                    escamara=false;
                    image1Set = true;
                    imagenesSeleccionadasBitmap.add(thumbnail);
                    imagenesSeleccionadasUri.add(imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if ((resultCode==RESULT_OK && !image2Set) || (escamara==true && !image2Set)){
            if (escamara == false) {
                Uri path = data.getData();
                imagen2.setImageURI(path);
                image2Set = true;
                BitmapDrawable drawable1 = (BitmapDrawable) imagen2.getDrawable();
                Bitmap bitmap1 = drawable1.getBitmap();
                imagenesSeleccionadasBitmap.add(bitmap1);
                imagenesSeleccionadasUri.add(path);
            }else{
                image2Set = true;
                try {
                    thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    imagen2.setImageBitmap(thumbnail);
                    imageurl = getRealPathFromURI(imageUri);
                    escamara=false;
                    imagenesSeleccionadasBitmap.add(thumbnail);
                    imagenesSeleccionadasUri.add(imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if ((resultCode==RESULT_OK && !image3Set) || (escamara==true && !image3Set)){
            if (escamara == false) {
                Uri path = data.getData();
                imagen3.setImageURI(path);
                image3Set = true;
                BitmapDrawable drawable2 = (BitmapDrawable) imagen3.getDrawable();
                Bitmap bitmap2 = drawable2.getBitmap();
                imagenesSeleccionadasBitmap.add(bitmap2);
                imagenesSeleccionadasUri.add(path);
            }else{
                image3Set = true;
                try {
                    thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    imagen3.setImageBitmap(thumbnail);
                    imageurl = getRealPathFromURI(imageUri);
                    escamara=false;
                    imagenesSeleccionadasBitmap.add(thumbnail);
                    imagenesSeleccionadasUri.add(imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if ((resultCode==RESULT_OK && !image4Set) || (escamara==true && !image4Set)){
            if (escamara == false) {
                Uri path=data.getData();
                imagen4.setImageURI(path);
                image4Set = true;
                BitmapDrawable drawable3 = (BitmapDrawable) imagen4.getDrawable();
                Bitmap bitmap3 = drawable3.getBitmap();
                imagenesSeleccionadasBitmap.add(bitmap3);
                imagenesSeleccionadasUri.add(path);
            }else{
                image4Set = true;
                try {
                    thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    imagen4.setImageBitmap(thumbnail);
                    imageurl = getRealPathFromURI(imageUri);
                    escamara=false;
                    imagenesSeleccionadasBitmap.add(thumbnail);
                    imagenesSeleccionadasUri.add(imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if ((resultCode==RESULT_OK && !image5Set)|| (escamara==true && !image5Set)){
            if (escamara == false) {
                Uri path = data.getData();
                imagen5.setImageURI(path);
                image5Set = true;
                BitmapDrawable drawable4 = (BitmapDrawable) imagen5.getDrawable();
                Bitmap bitmap4 = drawable4.getBitmap();
                imagenesSeleccionadasBitmap.add(bitmap4);
                imagenesSeleccionadasUri.add(path);
            }else{
                image5Set = true;
                try {
                    thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    imagen5.setImageBitmap(thumbnail);
                    imageurl = getRealPathFromURI(imageUri);
                    escamara=false;
                    imagenesSeleccionadasBitmap.add(thumbnail);
                    imagenesSeleccionadasUri.add(imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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

    private boolean checkExternalStoragePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permiso no garantizado");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i(TAG, "No tienes permiso");
            return true;
        }

        return false;
    }
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}