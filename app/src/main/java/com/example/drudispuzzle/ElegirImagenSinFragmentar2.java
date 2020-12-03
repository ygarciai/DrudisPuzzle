package com.example.drudispuzzle;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ElegirImagenSinFragmentar2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ElegirImagenSinFragmentar2 extends AppCompatActivity implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ElegirImagenSinFragmentar2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ElegirImagenSinFragmentar2.
     */
    // TODO: Rename and change types and number of parameters
    public static ElegirImagenSinFragmentar2 newInstance(String param1, String param2) {
        ElegirImagenSinFragmentar2 fragment = new ElegirImagenSinFragmentar2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_elegir_imagen_sin_fragmentar2);
        ApplicationLifecycleHandler handler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(handler);
        registerComponentCallbacks(handler);

        AssetManager am = getAssets();
        try {
            final String[] files  = am.list("img");

            GridView grid = findViewById(R.id.grid);
            grid.setAdapter(new AdaptadorImagen(this));
        } catch (IOException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elegir_imagen_sin_fragmentar2, container, false);
    }

    @Override
    public void onClick(View v) {

    }
}