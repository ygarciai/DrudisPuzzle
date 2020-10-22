package com.example.drudispuzzle;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectionActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectionActivity  extends AppCompatActivity implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectionActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectionActivity.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectionActivity newInstance(String param1, String param2) {
        SelectionActivity fragment = new SelectionActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_selection_activity);
        Button btnPlay = (Button) findViewById(R.id.button_inicioPartida);
        Button btnRank = (Button) findViewById(R.id.button_ranking);
        Button btnMult = (Button) findViewById(R.id.button_Multiplayer);
        Button btnFoto = (Button) findViewById(R.id.button_tomarFoto);
        btnPlay.setOnClickListener(this);
        btnRank.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnFoto.setOnClickListener(this);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_selection_activity, container, false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_inicioPartida:
                Intent intent = new Intent(view.getContext(), SeleccionTipoPuzzle.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.button_ranking:
                Intent intent1 = new Intent(view.getContext(), Ranking.class);
                startActivityForResult(intent1, 0);
                break;
            case R.id.button_Multiplayer:
                Intent intent2 = new Intent(view.getContext(), Multiplayer.class);
                startActivityForResult(intent2, 0);
                break;
            case R.id.button_tomarFoto:
                Intent intent3 = new Intent(view.getContext(), TomarFoto.class);
                startActivityForResult(intent3, 0);
                break;
        }

    }
}