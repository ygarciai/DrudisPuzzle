package com.example.drudispuzzle;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.method.Touch;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

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

    ImageView i1;

    int numberPieces;
    int level, rows, cols, chunkHeight, chunkWidth;
    ArrayList<Uri> imageList;



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
        level = 1;

        //ArrayList<Uri> imageList = (ArrayList<Uri>) getIntent().getSerializableExtra("imagenesSeleccionadasUri");
        final Chronometer myChronometer = findViewById(R.id.chronometer);
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

        if (level == 1){
            i1.setImageURI(imageList1.get(0));

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
            }
        } else if (level==2) {

            i1.setImageURI(imageList1.get(1));

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
            }

        } else if (level==3) {
            i1.setImageURI(imageList1.get(2));
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
            }
        } else if (level==4) {
            i1.setImageURI(imageList1.get(3));

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
            }
        } else if (level==5) {
            i1.setImageURI(imageList1.get(4));

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
            }

        } else if (level==6){

            Chronometer myChronometer = findViewById(R.id.chronometer);
            myChronometer.stop();


            String tiempo = myChronometer.getText().toString();
            

            setContentView(R.layout.activity_records);


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
        ImageView imageView = (ImageView) view;
        Bitmap bitmap = emptyChunk();
        imageView.setImageBitmap(bitmap);
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
}