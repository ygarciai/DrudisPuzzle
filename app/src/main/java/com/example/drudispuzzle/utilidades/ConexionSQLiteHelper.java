package com.example.drudispuzzle.utilidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;


public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    //añado
    private static final String CREAR_TABLA_PLAYER = "CREATE TABLE player (name TEXT, time TEXT)";
    private static final String DB_NAME = "player.sqlite";
    private static final int DB_VERSION = 1;
    public ConexionSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    public void ConexionSQLiteHelper1() {
    }

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Utilidades.CREAR_TABLA_PLAYER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS "+Utilidades.TABLA_PLAYER);
        onCreate(db);
    }
}
