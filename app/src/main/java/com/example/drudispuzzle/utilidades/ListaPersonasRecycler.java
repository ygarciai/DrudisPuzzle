package com.example.drudispuzzle.utilidades;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drudispuzzle.R;
import com.example.drudispuzzle.adaptadores.ListaPersonasAdapter;
import com.example.drudispuzzle.entidades.Usuario;

import java.util.ArrayList;


public class ListaPersonasRecycler extends AppCompatActivity {

    ArrayList<Usuario> listaUsuario;
    RecyclerView recyclerViewUsuarios;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout._activity_lista_personas_recycler);

        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);

        listaUsuario=new ArrayList<>();

        recyclerViewUsuarios= (RecyclerView) findViewById(R.id.recyclerJugadores);
        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(this));

        consultarListaPersonas();

        ListaPersonasAdapter adapter=new ListaPersonasAdapter(listaUsuario);
        recyclerViewUsuarios.setAdapter(adapter);

    }

    private void consultarListaPersonas() {
        SQLiteDatabase db=conn.getReadableDatabase();

        Usuario usuario=null;

        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_PLAYER,null);

        while (cursor.moveToNext()){
            usuario=new Usuario();
            usuario.setP(cursor.getString(0));
            usuario.setT(cursor.getString(1));

            listaUsuario.add(usuario);
        }
    }

}
