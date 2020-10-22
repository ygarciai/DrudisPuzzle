package com.example.drudispuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.botoninicio);
        Button btn2= findViewById(R.id.salir);
        Button btn3 = (Button) findViewById(R.id.button);
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        nombre= (EditText) findViewById (R.id.nombreplayer );

        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.salir:
                finish();
                break;
            case R.id.botoninicio:
                Intent intent = new Intent(view.getContext(), ElegirPuzzle.class);
                intent.putExtra("nombrejugador",nombre.getText().toString());
                startActivityForResult(intent, 0);
                break;
            case R.id.button:
                Intent intent1 = new Intent(view.getContext(), SelectionActivity.class);
                startActivityForResult(intent1, 0);
                break;
        }

    }
}
