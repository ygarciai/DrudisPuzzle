package com.example.drudispuzzle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PartidaFragmentada2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartidaFragmentada2 extends AppCompatActivity implements View.OnTouchListener, View.OnDragListener {

    private static final String LOGCAT = null;

    Drawable image_gollumEntero;
    Drawable imagen_gollum1;
    Drawable imagen_gollum2;
    Drawable imagen_gollum3;
    Drawable imagen_gollum4;
    Drawable imagen_gollum5;
    Drawable imagen_gollum6;
    Drawable imagen_gollum7;
    Drawable imagen_gollum8;
    Drawable imagen_gollum9;
    ImageView i0,i1,i2,i3,i4,i5,i6,i7,i8,i9;
    int rows = 3;
    int cols = 3;
    private List<Bitmap> pieces;
    private List<Integer> indexArray;
    int numberPieces=9;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PartidaFragmentada2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PartidaFragmentada2.
     */
    // TODO: Rename and change types and number of parameters
    public static PartidaFragmentada2 newInstance(String param1, String param2) {
        PartidaFragmentada2 fragment = new PartidaFragmentada2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_partida_fragmentada2);
        ApplicationLifecycleHandler handler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(handler);
        registerComponentCallbacks(handler);


        ImageView i0 = new ImageView(this);
        ImageView i1 = new ImageView(this);
        ImageView i2 = new ImageView(this);
        ImageView i3 = new ImageView(this);
        ImageView i4 = new ImageView(this);
        ImageView i5 = new ImageView(this);
        ImageView i6 = new ImageView(this);
        ImageView i7 = new ImageView(this);
        ImageView i8 = new ImageView(this);
        ImageView i9 = new ImageView(this);



        Drawable image_gollumEntero  = ContextCompat.getDrawable(getApplicationContext(), R.drawable.gollumentero);
        Drawable imagen_gollum1  = ContextCompat.getDrawable(getApplicationContext(), R.drawable.golum1);
        Drawable imagen_gollum2  = ContextCompat.getDrawable(getApplicationContext(), R.drawable.golum2);
        Drawable imagen_gollum3  = ContextCompat.getDrawable(getApplicationContext(), R.drawable.golum3);
        Drawable imagen_gollum4  = ContextCompat.getDrawable(getApplicationContext(), R.drawable.golum4);
        Drawable imagen_gollum5  = ContextCompat.getDrawable(getApplicationContext(), R.drawable.golum5);
        Drawable imagen_gollum6  = ContextCompat.getDrawable(getApplicationContext(), R.drawable.golum6);
        Drawable imagen_gollum7  = ContextCompat.getDrawable(getApplicationContext(), R.drawable.golum7);
        Drawable imagen_gollum8  = ContextCompat.getDrawable(getApplicationContext(), R.drawable.golum8);
        Drawable imagen_gollum9  = ContextCompat.getDrawable(getApplicationContext(), R.drawable.golum9);

        i0.setImageResource(R.drawable.gollumentero);
        i1.setImageResource(R.drawable.golum1);
        i2.setImageResource(R.drawable.golum2);
        i3.setImageResource(R.drawable.golum3);
        i4.setImageResource(R.drawable.golum4);
        i5.setImageResource(R.drawable.golum5);
        i6.setImageResource(R.drawable.golum6);
        i7.setImageResource(R.drawable.golum7);
        i8.setImageResource(R.drawable.golum8);
        i9.setImageResource(R.drawable.golum9);
        initgame(i0,i1,i2,i3,i4,i5,i6,i7,i8,i9);
    }

    private void initgame(ImageView i0,ImageView i1,ImageView i2,ImageView i3,ImageView i4,ImageView i5,ImageView i6,ImageView i7,ImageView i8,ImageView i9) {
        //GridLayout layout = findViewById(R.id.secondLinearLayout);
        //GridLayout layout2 = findViewById(R.id.thirdLinearLayout);

        BitmapDrawable drawable0 = (BitmapDrawable) i0.getDrawable();
        BitmapDrawable drawable1 = (BitmapDrawable) i1.getDrawable();
        BitmapDrawable drawable2 = (BitmapDrawable) i2.getDrawable();
        BitmapDrawable drawable3 = (BitmapDrawable) i3.getDrawable();
        BitmapDrawable drawable4 = (BitmapDrawable) i4.getDrawable();
        BitmapDrawable drawable5 = (BitmapDrawable) i5.getDrawable();
        BitmapDrawable drawable6 = (BitmapDrawable) i6.getDrawable();
        BitmapDrawable drawable7 = (BitmapDrawable) i7.getDrawable();
        BitmapDrawable drawable8 = (BitmapDrawable) i8.getDrawable();
        BitmapDrawable drawable9 = (BitmapDrawable) i9.getDrawable();


        Bitmap bitmap0 = drawable0.getBitmap();
        Bitmap bitmap1 = drawable1.getBitmap();
        Bitmap bitmap2 = drawable2.getBitmap();
        Bitmap bitmap3 = drawable3.getBitmap();
        Bitmap bitmap4 = drawable4.getBitmap();
        Bitmap bitmap5 = drawable5.getBitmap();
        Bitmap bitmap6 = drawable6.getBitmap();
        Bitmap bitmap7 = drawable7.getBitmap();
        Bitmap bitmap8 = drawable8.getBitmap();
        Bitmap bitmap9 = drawable9.getBitmap();

        Bitmap scaledBitmap0 = Bitmap.createScaledBitmap(bitmap0, bitmap0.getWidth(), bitmap0.getHeight(), true);
        Bitmap scaledBitmap1 = Bitmap.createScaledBitmap(bitmap1, 100, 100, true);
        Bitmap scaledBitmap2 = Bitmap.createScaledBitmap(bitmap2, 100, 100, true);
        Bitmap scaledBitmap3 = Bitmap.createScaledBitmap(bitmap3, 100, 100, true);
        Bitmap scaledBitmap4 = Bitmap.createScaledBitmap(bitmap4, 100, 100, true);
        Bitmap scaledBitmap5 = Bitmap.createScaledBitmap(bitmap5, 100, 100, true);
        Bitmap scaledBitmap6 = Bitmap.createScaledBitmap(bitmap6, 100, 100, true);
        Bitmap scaledBitmap7 = Bitmap.createScaledBitmap(bitmap7, 100, 100, true);
        Bitmap scaledBitmap8 = Bitmap.createScaledBitmap(bitmap8, 100, 100, true);
        Bitmap scaledBitmap9 = Bitmap.createScaledBitmap(bitmap9, 100, 100, true);


        //ImageView ii1 = new ImageView(getApplicationContext());
        //ImageView ii2 = new ImageView(getApplicationContext());
        //ImageView ii3 = new ImageView(getApplicationContext());
        //ImageView ii4 = new ImageView(getApplicationContext());
        //ImageView ii5 = new ImageView(getApplicationContext());
        //ImageView ii6 = new ImageView(getApplicationContext());
        //ImageView ii7 = new ImageView(getApplicationContext());
        //ImageView ii8 = new ImageView(getApplicationContext());
        //ImageView ii9 = new ImageView(getApplicationContext());

        //ImageView iv = new ImageView(getApplicationContext());

        //iv.setImageBitmap(scaledBitmap1);
        //layout.addView(iv);


        //iv.setImageBitmap(scaledBitmap2);
        //iv.setOnTouchListener(this);        //layout.addView(iv);





        //ii1.setImageBitmap(scaledBitmap1);
        //layout.addView(ii1);
        //ii2.setImageBitmap(scaledBitmap2);
        //layout.addView(ii2);
        //ii3.setImageBitmap(scaledBitmap3);
        //layout.addView(ii3);
        //ii4.setImageBitmap(scaledBitmap4);
        //layout.addView(ii4);
        //ii5.setImageBitmap(scaledBitmap5);
        //layout.addView(ii5);
        //ii6.setImageBitmap(scaledBitmap6);
        //layout.addView(ii6);
        //ii7.setImageBitmap(scaledBitmap7);
        //layout.addView(ii7);
        //ii8.setImageBitmap(scaledBitmap8);
        //layout.addView(ii8);
        //ii9.setImageBitmap(scaledBitmap9);
        //layout.addView(ii9);



        //ImageView emptyView = new ImageView(getApplicationContext());
        //emptyView.setImageBitmap(scaledBitmap0);
        //emptyView.setAlpha((float) 0.1);
        //emptyView.setOnDragListener(this);
        //layout2.addView(emptyView);

        ArrayList<Uri> imageList1 = (ArrayList<Uri>) getIntent().getSerializableExtra("imagenesSeleccionadasUri");
        GridLayout layout = findViewById(R.id.secondLinearLayout);
        GridLayout layout2 = findViewById(R.id.thirdLinearLayout);
        layout.removeAllViews();
        layout.setColumnCount(cols);
        layout.setRowCount(rows);

        layout2.removeAllViews();
        layout2.setColumnCount(cols);
        layout2.setRowCount(rows);


        //inicializarListaImagenes;
        List<Drawable> listaPiezas = new ArrayList<>();
        pieces = new ArrayList<>();
        listaPiezas.add(drawable1);
        listaPiezas.add(drawable2);
        listaPiezas.add(drawable3);
        listaPiezas.add(drawable4);
        listaPiezas.add(drawable5);
        listaPiezas.add(drawable6);
        listaPiezas.add(drawable7);
        listaPiezas.add(drawable8);
        listaPiezas.add(drawable9);
        pieces.add(scaledBitmap1);
        pieces.add(scaledBitmap2);
        pieces.add(scaledBitmap3);
        pieces.add(scaledBitmap4);
        pieces.add(scaledBitmap5);
        pieces.add(scaledBitmap6);
        pieces.add(scaledBitmap7);
        pieces.add(scaledBitmap8);
        pieces.add(scaledBitmap9);

        int count = 0;
        indexArray = new ArrayList<>();
        for(Bitmap piece : pieces) {
            indexArray.add(count);
            count++;
        }
        Collections.shuffle(indexArray);
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
                    Intent intent1 = new Intent(view.getContext(), SelectionActivity.class);
                    startActivityForResult(intent1, 0);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_partida_fragmentada2, container, false);
    }
}