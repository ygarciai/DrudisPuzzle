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
        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);

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
                Intent intent = new Intent(view.getContext(), SelectionActivity.class);
                startActivityForResult(intent, 0);
                break;
        }

    }
}
