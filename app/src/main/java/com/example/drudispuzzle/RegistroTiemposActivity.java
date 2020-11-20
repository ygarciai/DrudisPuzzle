package com.example.drudispuzzle;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drudispuzzle.utilidades.Utilidades;

public class RegistroTiemposActivity extends AppCompatActivity {
    EditText campoName, campoTime;

    ConexionSQLiteHelper conn;
    private View CalenRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);


        campoName= (EditText) findViewById(R.id.campoName);
        // no es EditText pues sera el tiempo del contador
        campoTime= (EditText) findViewById(R.id.campoTime);

        //añado CalenRegistro y la funcion RegCalendar()
        CalenRegistro = findViewById(R.id.btnRegistro);

    }

    public void onClick (View view) {
        registroRecords();
        registroRecordsSql();
        RegCalendar();

    }

    private void registroRecordsSql() {
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this, "bd player", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        // Insert into player (nombre, time) values (KingOfPuzzles, 00:10);
        String insert="INSERT INTO "+Utilidades.TABLA_PLAYER
                +" (" +Utilidades.CAMPO_NAME+","+Utilidades.CAMPO_TIME+") "+
                " VALUES ('"+campoName.getText().toString()+"'," +
                            +campoTime.getDrawingTime()+")";


        db.execSQL(insert);

        db.close();

    }

    private void registroRecords() {
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this, "bd player", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_NAME, campoName.getText().toString());
        values.put(Utilidades.CAMPO_TIME, campoTime.getDrawingTime());

        Long idResultante=db.insert(Utilidades.TABLA_PLAYER, Utilidades.CAMPO_TIME, values);

        Toast.makeText(getApplicationContext(),"Tiempo resultante: "+idResultante, Toast.LENGTH_SHORT);
        db.close();

    }

    private void RegCalendar() {
        CalenRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!campoName.getText().toString().isEmpty() && !campoTime.getText().toString().isEmpty()) {

                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);
                    intent.putExtra(CalendarContract.Events.ACCOUNT_NAME, campoName.getText().toString());
                    intent.putExtra(CalendarContract.Events.EVENT_TIMEZONE, campoTime.getText().toString());
                    intent.putExtra(CalendarContract.Events.ALL_DAY, "true");

                    if(intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }

                }

            }
        });
    }
}
