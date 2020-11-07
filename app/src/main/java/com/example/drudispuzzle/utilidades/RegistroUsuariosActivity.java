package com.example.drudispuzzle.utilidades;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drudispuzzle.R;

public class RegistroUsuariosActivity extends AppCompatActivity {

    EditText campoNoombre, campoTiempoo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._activity_registro_usuarios);

        campoNoombre = (EditText) findViewById(R.id.campoNamee);
        campoTiempoo = (EditText) findViewById(R.id.campoTimee);


    }

    public void onClick(View view) {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();
        db.execSQL(Utilidades.CREAR_TABLA_PLAYER);
        registrarUsuarios();


    }

    public void registrarUsuariosSql() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();


        String insert="INSERT INTO "+Utilidades.TABLA_PLAYER
                +" ( " +Utilidades.CAMPO_NAME +","+Utilidades.CAMPO_TIME +")" +
                " VALUES ("+ campoNoombre.getText().toString()+", '"+ campoTiempoo.getText().toString()+"')";

        db.execSQL(insert);


        db.close();
    }


    public void registrarUsuarios() {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
        SQLiteDatabase db=conn.getWritableDatabase();



        ContentValues values=new ContentValues();
        values.put(Utilidades.CAMPO_NAME, campoNoombre.getText().toString());
        values.put(Utilidades.CAMPO_TIME, campoTiempoo.getText().toString());

        Long idResultante=db.insert(Utilidades.TABLA_PLAYER,Utilidades.CAMPO_NAME,values);

        Toast.makeText(getApplicationContext(),"Nombre Registro: "+idResultante,Toast.LENGTH_SHORT).show();
        db.close();
    }
}
