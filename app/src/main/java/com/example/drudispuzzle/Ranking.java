package com.example.drudispuzzle;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.drudispuzzle.adaptadores.ListaPersonasAdapter;
import com.example.drudispuzzle.entidades.Usuario;
import com.example.drudispuzzle.utilidades.Utilidades;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.GET;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Ranking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ranking extends AppCompatActivity {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String TAG;
    ArrayList listaUsuarioFinal;
    ArrayList<Usuario> listaUsuarioFinal2;
    Usuario usuarioañadir;
    boolean acabado=false;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaPersonasAdapter adapter;

    ArrayList<Usuario> listaUsuario;
    RecyclerView recyclerViewUsuarios;
    ConexionSQLiteHelper conn;

    public Ranking() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ranking.
     */
    // TODO: Rename and change types and number of parameters
    public static Ranking newInstance(String param1, String param2) {
        Ranking fragment = new Ranking();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        usuarioañadir=new Usuario();
        listaUsuarioFinal2= new ArrayList<Usuario>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout._activity_lista_personas_recycler);

        ApplicationLifecycleHandler handler = new ApplicationLifecycleHandler();
        registerActivityLifecycleCallbacks(handler);
        registerComponentCallbacks(handler);


        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);

        listaUsuario=new ArrayList<>();
        listaUsuarioFinal=new ArrayList<>();

        recyclerViewUsuarios= (RecyclerView) findViewById(R.id.recyclerJugadores);
        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(this));

        consultarListaPersonas();
        

        readData(new FirestoreCallback() {
           @Override
            public void onCallback(ArrayList listaUsuarioFinal) {
                for (int i=0;i<listaUsuarioFinal.size();i++){
                    usuarioañadir=new Usuario();
                    HashMap hashmap = (HashMap) listaUsuarioFinal.get(i);
                    HashMap hashmap1 = (HashMap) listaUsuarioFinal.get(i);
                    String value = (String) hashmap.get("Puntuacion");
                    String whatever = value;
                    String value1=(String) hashmap1.get("Nombre");
                    String whatever2 = value1;
                    usuarioañadir.setP(value1);
                    usuarioañadir.setT(value);
                    listaUsuarioFinal2.add(usuarioañadir);
                }
                ListaPersonasAdapter adapter=new ListaPersonasAdapter(listaUsuarioFinal2);
                recyclerViewUsuarios.setAdapter(adapter);
            }
        });



    }

    private void recuperarFirestore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Puntuaciones").orderBy("Puntuacion").limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                listaUsuarioFinal.add(document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }
    private void readData(final FirestoreCallback firestoreCallback){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Puntuaciones").orderBy("Puntuacion").limit(10)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                listaUsuarioFinal.add(document.getData());
                            }
                            firestoreCallback.onCallback(listaUsuarioFinal);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private interface FirestoreCallback{
        void onCallback(ArrayList listaUsuarioFinal );
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout._activity_lista_personas_recycler, container, false);
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