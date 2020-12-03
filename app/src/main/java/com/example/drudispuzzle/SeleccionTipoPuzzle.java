package com.example.drudispuzzle;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.drudispuzzle.utilidades.Utilidades;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeleccionTipoPuzzle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeleccionTipoPuzzle  extends AppCompatActivity implements View.OnClickListener  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public SeleccionTipoPuzzle() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1XDDDDDDDDDDDDDDDDDDDDDDDDDD.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeleccionTipoPuzzle.
     */
    // TODO: Rename and change types and number of parameters
    public static SeleccionTipoPuzzle newInstance(String param1, String param2) {
        SeleccionTipoPuzzle fragment = new SeleccionTipoPuzzle();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_seleccion_tipo_puzzle);
        ApplicationLifecycleHandler handler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(handler);
        registerComponentCallbacks(handler);


        Button btn = (Button) findViewById(R.id.button_Imagenes);
        Button btn1 = (Button) findViewById(R.id.button_ImagenesSeg);
        btn.setOnClickListener(this);
        btn1.setOnClickListener(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_seleccion_tipo_puzzle, container, false);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_Imagenes:
                Intent intent = new Intent(view.getContext(), ElegirImagenSinFragmentar.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.button_ImagenesSeg:
                Intent intent1 = new Intent(view.getContext(), PartidaFragmentada2.class);
                startActivityForResult(intent1, 0);
                break;
        }
    }

}