package com.example.drudispuzzle;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drudispuzzle.utilidades.RegistroUsuariosActivity;

import java.text.BreakIterator;

public class CalendarioPuntuacion extends AppCompatActivity {

    EditText NAME;
    EditText TIME;
    Button CalenRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._activity_registro_usuarios);
        ApplicationLifecycleHandler handler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(handler);
        registerComponentCallbacks(handler);

        NAME = findViewById(R.id.campoNamee);
        TIME = findViewById(R.id.campoTimee);
        CalenRegistro = findViewById(R.id.btnRegistro);

        CalenRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NAME.getText().toString().isEmpty() && !TIME.getText().toString().isEmpty()) {

                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);
                    intent.putExtra(CalendarContract.Events.ACCOUNT_NAME, NAME.getText().toString());
                    intent.putExtra(CalendarContract.Events.EVENT_TIMEZONE, TIME.getText().toString());
                    intent.putExtra(CalendarContract.Events.ALL_DAY, "true");

                    if(intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }

                }

            }
        });
    }
}

    
            
