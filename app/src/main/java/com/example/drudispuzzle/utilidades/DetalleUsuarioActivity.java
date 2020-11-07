package com.example.drudispuzzle.utilidades;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drudispuzzle.R;
import com.example.drudispuzzle.entidades.Usuario;


public class DetalleUsuarioActivity extends AppCompatActivity {

    TextView campoNA, campoTI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._activity_detalle_usuario);

        campoNA = (TextView) findViewById(R.id.campoNamee);
        campoTI = (TextView) findViewById(R.id.campoTimee);

        Bundle objetoEnviado=getIntent().getExtras();
        Usuario user=null;

        if(objetoEnviado!=null){
            user= (Usuario) objetoEnviado.getSerializable("usuario");
            campoNA.setText(user.getP().toString());
            campoTI.setText(user.getT().toString());

        }

    }
}
