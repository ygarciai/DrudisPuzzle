package com.example.drudispuzzle;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.text.Layout;
import android.text.method.Touch;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.drudispuzzle.entidades.Usuario;
import com.example.drudispuzzle.utilidades.RegistroUsuariosActivity;
import com.example.drudispuzzle.utilidades.Utilidades;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jakewharton.rxbinding3.widget.RxTextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static java.lang.StrictMath.abs;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartidaSinFragmentar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartidaSinFragmentar extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, View.OnDragListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String LOGCAT = null;
    private List<Bitmap> pieces;
    private List<Integer> indexArray;

    public ArrayList<Usuario> listaUsuariosRecordos;
    String Maximoactual="";


    ImageView i1;
    ImageView i1red;

    int numberPieces;
    int level, rows=2, cols=2, chunkHeight, chunkWidth;
    boolean acabado=false;
    ArrayList<Uri> imageList;
    RegistroUsuariosActivity Registro;
    ConexionSQLiteHelper Inicializar;
    Utilidades utilidades;
    Chronometer myChronometer;
    String tiempo;



    @BindView(R.id.campoNamee) TextView nombreIntroducido;
    @BindView(R.id.btnRegistro) Button btnGuardar;
    @BindView(R.id.campoTimee) TextView chronometro;


    public boolean superarecord=false;

    //añado
    static boolean reproduciendo=false;
    public MediaPlayer mp2 = null;
    public MediaPlayer mp3 = null;
    public MediaPlayer mp4 = null;

    String TAG;



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
        superarecord=false;
        ApplicationLifecycleHandler handler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(handler);
        registerComponentCallbacks(handler);

        Registro=new RegistroUsuariosActivity();
        level = 1;
        Inicializar=new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);
        utilidades=new Utilidades();

        //nombreIntroducido=findViewById(R.id.campoNamee);

        final Chronometer myChronometer = findViewById(R.id.chronometer);

        //añado
        mp2 = new MediaPlayer();
        mp2 = MediaPlayer.create(this, R.raw.beep);
        mp3 = new MediaPlayer();
        mp3 = MediaPlayer.create(this, R.raw.aplausos);
        mp4 = new MediaPlayer();
        mp4 = MediaPlayer.create(this, R.raw.piecesong);

        listaUsuariosRecordos=new ArrayList<>();


        myChronometer.start();
        i1 = findViewById(R.id.imageView_fondoPantalla);
        initGame(level, imageList);

   }

    private void initGame(int level, ArrayList<Uri> imageList) {
        ArrayList<Uri> imageList1 = (ArrayList<Uri>) getIntent().getSerializableExtra("imagenesSeleccionadasUri");
        GridLayout layout = findViewById(R.id.secondLinearLayout);
        GridLayout layout2 = findViewById(R.id.thirdLinearLayout);
        layout.removeAllViews();
        layout2.removeAllViews();
        FirebaseStorage storage = FirebaseStorage.getInstance();

        InputStream in=null;

        if (level == 1){
            i1.setImageURI(imageList1.get(0));
            try {
                in = new URL((imageList1.get(0).toString())).openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap mIcon11 = BitmapFactory.decodeStream(in);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(mIcon11, 250, 250, true);

            i1.setImageBitmap(scaledBitmap);
            splitImage(i1);

            layout.setColumnCount(cols);
            layout2.setColumnCount(cols);

            for(int i = 0; i < indexArray.size(); i++) {
                Bitmap piece = pieces.get(indexArray.get(i));
                ImageView iv = new ImageView(getApplicationContext());
                iv.setImageBitmap(piece);
                iv.setOnTouchListener(this);
                layout.addView(iv);

                ImageView emptyView = new ImageView(getApplicationContext());
                emptyView.setImageBitmap(pieces.get(i));
                emptyView.setAlpha((float) 0.1);
                emptyView.setOnDragListener(this);
                layout2.addView(emptyView);
                mp2.start();
                reproduciendo=true;

            }
        } else if (level==2) {
            //cols = cols + 1;

            i1.setImageURI(imageList1.get(1));

            try {
                in = new URL((imageList1.get(1).toString())).openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap mIcon11 = BitmapFactory.decodeStream(in);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(mIcon11, 250, 250, true);

            i1.setImageBitmap(scaledBitmap);

            splitImage(i1);
            layout.setColumnCount(cols);
            layout2.setColumnCount(cols);

            for(int i = 0; i < indexArray.size(); i++) {
                Bitmap piece = pieces.get(indexArray.get(i));
                ImageView iv = new ImageView(getApplicationContext());
                iv.setImageBitmap(piece);
                iv.setOnTouchListener(this);
                layout.addView(iv);

                ImageView emptyView = new ImageView(getApplicationContext());
                emptyView.setImageBitmap(pieces.get(i));
                emptyView.setAlpha((float) 0.1);
                emptyView.setOnDragListener(this);
                layout2.addView(emptyView);
                mp2.start();
                reproduciendo=true;
            }

        } else if (level==3) {

            //rows = rows +1;

            i1.setImageURI(imageList1.get(2));

            try {
                in = new URL((imageList1.get(2).toString())).openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap mIcon11 = BitmapFactory.decodeStream(in);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(mIcon11, 250, 250, true);

            i1.setImageBitmap(scaledBitmap);

            splitImage(i1);

            layout.setColumnCount(cols);
            layout2.setColumnCount(cols);


            for(int i = 0; i < indexArray.size(); i++) {
                Bitmap piece = pieces.get(indexArray.get(i));
                ImageView iv = new ImageView(getApplicationContext());

                iv.setImageBitmap(piece);
                iv.setOnTouchListener(this);
                layout.addView(iv);

                ImageView emptyView = new ImageView(getApplicationContext());
                emptyView.setImageBitmap(pieces.get(i));
                emptyView.setAlpha((float) 0.1);
                emptyView.setOnDragListener(this);
                layout2.addView(emptyView);
                mp2.start();
                reproduciendo=true;

            }

        } else if (level==4) {
            //cols = cols + 1;
            i1.setImageURI(imageList1.get(3));

            try {
                in = new URL((imageList1.get(3).toString())).openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap mIcon11 = BitmapFactory.decodeStream(in);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(mIcon11, 250, 250, true);


            i1.setImageBitmap(scaledBitmap);

            splitImage(i1);
            layout.setColumnCount(cols);
            layout2.setColumnCount(cols);

            for(int i = 0; i < indexArray.size(); i++) {
                Bitmap piece = pieces.get(indexArray.get(i));
                ImageView iv = new ImageView(getApplicationContext());
                iv.setImageBitmap(piece);
                iv.setOnTouchListener(this);
                layout.addView(iv);

                ImageView emptyView = new ImageView(getApplicationContext());
                emptyView.setImageBitmap(pieces.get(i));
                emptyView.setAlpha((float) 0.1);
                emptyView.setOnDragListener(this);
                layout2.addView(emptyView);
                mp2.start();
                reproduciendo=true;
            }

        } else if (level==5) {

            //rows = rows +1;

            i1.setImageURI(imageList1.get(4));

            try {
                in = new URL((imageList1.get(4).toString())).openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Bitmap mIcon11 = BitmapFactory.decodeStream(in);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(mIcon11, 250, 250, true);
            i1.setImageBitmap(scaledBitmap);

            splitImage(i1);
            layout.setColumnCount(cols);
            layout2.setColumnCount(cols);

            for(int i = 0; i < indexArray.size(); i++) {
                Bitmap piece = pieces.get(indexArray.get(i));
                ImageView iv = new ImageView(getApplicationContext());
                iv.setImageBitmap(piece);
                iv.setOnTouchListener(this);
                layout.addView(iv);

                ImageView emptyView = new ImageView(getApplicationContext());
                emptyView.setImageBitmap(pieces.get(i));
                emptyView.setAlpha((float) 0.1);
                emptyView.setOnDragListener(this);
                layout2.addView(emptyView);
                mp2.start();
                reproduciendo=true;

            }

        } else if (level==6){
            acabado=true;

            myChronometer = findViewById(R.id.chronometer);
            myChronometer.stop();

            tiempo = myChronometer.getText().toString();

            // Animacion Funciona
            setContentView(R.layout._activity_registro_usuarios);
            ButterKnife.bind(this);
            RelativeLayout VentanaUp = (RelativeLayout) findViewById(R.id.VentanaUp);
            Animation itemAnimation = AnimationUtils.loadAnimation(this,R.anim.item_animation);
            VentanaUp.startAnimation(itemAnimation);

            chronometro.setText(tiempo);

            //fin añadido

            btnGuardar.setOnClickListener(this);
            mp3.start();
            reproduciendo=true;

            Disposable ComprobacionRegistro = RxTextView.textChanges(nombreIntroducido)
                    .subscribe(new Consumer<CharSequence>() {
                        @Override
                        public void accept(CharSequence charSequence) throws  Exception{
                            //Add your logic to work on the Charsequence
                            String nombreintroducidocomp = nombreIntroducido.getText().toString();
                            if (nombreintroducidocomp.length() > 6) {
                                btnGuardar.setEnabled(true);
                            }else
                                btnGuardar.setEnabled(false);
                        }
                    });
        }
    }

    private void splitImage(ImageView image) {
        pieces = new ArrayList<>();

        BitmapDrawable drawable = (BitmapDrawable) image.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);



        chunkHeight = bitmap.getHeight()/rows;
        chunkWidth = bitmap.getWidth()/cols;

        int yCoord = 0;
        for(int x = 0; x < rows; x++){
            int xCoord = 0;
            for(int y = 0; y < cols; y++){
                pieces.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }

        int count = 0;
        indexArray = new ArrayList<>();
        for(Bitmap piece : pieces) {
            indexArray.add(count);
            count++;
        }

        Collections.shuffle(indexArray);
        numberPieces = pieces.size();
    }

    private Bitmap emptyChunk() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fondoblanco);
        Bitmap bmCopy = Bitmap.createScaledBitmap(bitmap, chunkWidth, chunkHeight, false);
        bmCopy.setWidth(chunkWidth);
        bmCopy.setHeight(chunkHeight);
        return bmCopy;
    }

    @Override
    public void onClick(View view) {
        //nombreIntroducido=findViewById(R.id.campoNamee);
        if (acabado=false){
            ImageView imageView = (ImageView) view;
            Bitmap bitmap = emptyChunk();
            imageView.setImageBitmap(bitmap);
        }
        switch (view.getId()) {
            case R.id.btnRegistro:

                ContentValues values=new ContentValues();

                com.example.drudispuzzle.utilidades.ConexionSQLiteHelper conn=new com.example.drudispuzzle.utilidades.ConexionSQLiteHelper(this,"bd_usuarios",null,1);
                SQLiteDatabase db=conn.getWritableDatabase();

                recuperarListaPersonas();
                try {
                    recuperarminimotiempo();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (superarecord){
                    Intent intent1 = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)

                            .putExtra(CalendarContract.Events.TITLE, "HAS LOGRADO RECORD CON:/Has achieved Record With: " + tiempo)
                            .putExtra(CalendarContract.Events.DESCRIPTION, "Yuju!")
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, "")
                            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                            .putExtra(Intent.EXTRA_EMAIL, "");
                    startActivityForResult(intent1, 0);
                }
                values.put(Utilidades.CAMPO_NAME, nombreIntroducido.getText().toString());
                values.put(Utilidades.CAMPO_TIME, tiempo);
                Long idResultante=db.insert(Utilidades.TABLA_PLAYER,Utilidades.CAMPO_NAME,values);
                Toast.makeText(getApplicationContext(),"Nombre Registro:/Name Register: "+idResultante,Toast.LENGTH_SHORT).show();
                db.close();

                FirebaseFirestore dbFB = FirebaseFirestore.getInstance();

                // Create a new user with a first, middle, and last name
                Map<String, Object> user = new HashMap<>();
                user.put("Nombre",  nombreIntroducido.getText().toString());
                user.put("Puntuacion", tiempo);


                // Add a new document with a generated ID
                dbFB.collection("Puntuaciones")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                            }
                        });

                Intent intent = new Intent(view.getContext(), SelectionActivity.class);
                startActivityForResult(intent, 0);
                break;

        }

    }

    private void recuperarListaPersonas() {

        com.example.drudispuzzle.utilidades.ConexionSQLiteHelper conn=new com.example.drudispuzzle.utilidades.ConexionSQLiteHelper(this,"bd_usuarios",null,1);

        SQLiteDatabase db=conn.getReadableDatabase();

        Usuario usuario=null;

        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_PLAYER,null);


        while (cursor.moveToNext()){
            usuario=new Usuario();
            usuario.setP(cursor.getString(0));
            usuario.setT(cursor.getString(1));

            listaUsuariosRecordos.add(usuario);
        }

    }

    private void recuperarminimotiempo() throws ParseException {

        String currentString =tiempo;
        String[] separated = currentString.split(":");
        Integer Minutos=0;
        Integer Segundos=0;
        Minutos=Integer.valueOf(separated[0]);
        Segundos=Integer.valueOf(separated[1]);
        Integer TotalSegundos=(Minutos*60)+Segundos;

        Integer ScoreMinimoAlmacenado=999999;

        for (int y=0;y<listaUsuariosRecordos.size();y++){
            String ScoreActual=listaUsuariosRecordos.get(y).getT();
            String[] separated1 = ScoreActual.split(":");
            Integer MinutosScore=0;
            Integer SegundosScore=0;
            MinutosScore=Integer.valueOf(separated1[0]);
            SegundosScore=Integer.valueOf(separated1[1]);
            Integer TotalSegundosScore=(MinutosScore*60)+(SegundosScore);
            if(TotalSegundosScore<ScoreMinimoAlmacenado){
                ScoreMinimoAlmacenado=TotalSegundosScore;
            }
        }
        if (TotalSegundos<ScoreMinimoAlmacenado){
            Maximoactual=tiempo;
            superarecord=true;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDragAndDrop(null, shadowBuilder, view, 0);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        int action = dragEvent.getAction();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                Log.d(LOGCAT, "Drag event started");
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                Log.d(LOGCAT, "Drag event entered into " + view.toString());
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                Log.d(LOGCAT, "Drag event exited from " + view.toString());
                break;
            case DragEvent.ACTION_DROP:
                Log.d(LOGCAT, "Dropped");
                View puzzleView = (View) dragEvent.getLocalState();
                ImageView puzzleImage = (ImageView) puzzleView;
                ImageView containerImage = (ImageView) view;
                Bitmap puzzleBitmap = ((BitmapDrawable) puzzleImage.getDrawable()).getBitmap();
                Bitmap containerBitmap = ((BitmapDrawable) containerImage.getDrawable()).getBitmap();
                if(puzzleBitmap == containerBitmap) {
                    puzzleView.setVisibility(View.INVISIBLE);
                    containerImage.setAlpha((float) 1);
                    numberPieces--;
                    mp4.start();
                    reproduciendo=true;
                }
                if(numberPieces == 0) {
                    level=level+1;
                    initGame(level,imageList);
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                Log.d(LOGCAT, "Drag ended");
                break;
            default:
                break;
        }
        return true;
    }

    //añado onActivityResult y play
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

        try {
            mp2.stop();
            mp2 = new MediaPlayer();
            mp2.setDataSource(context, uri);
            mp2.prepare();
            mp2.start();
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
        try {
            mp3.stop();
            mp3 = new MediaPlayer();
            mp3.setDataSource(context, uri);
            mp3.prepare();
            mp3.start();
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
        try {
            mp4.stop();
            mp4 = new MediaPlayer();
            mp4.setDataSource(context, uri);
            mp4.prepare();
            mp4.start();
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
