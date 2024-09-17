package com.wprotheus.pdm2atvpratica29jun2024.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLHelper extends SQLiteOpenHelper {
    public static final String ID_COL = "ID";
    public static final String NOME_COL = "NOME";
    public static final String NUMEROS_COL = "NUMEROS";
    public static final String SOMA_COL = "SOMA";
    public static final String RESULTADO_COL = "RESULTADO";
    public static final String IMAGEM_COL = "IMAGEM";
    static final String DB_NAME = "dbpartida.db";
    static final int DB_VERSION = 1;
    static final String TABLE_NAME = "tb_partida";
    static final String query = "CREATE TABLE " + TABLE_NAME
            + " ("
            + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NOME_COL + " TEXT NOT NULL, "
            + NUMEROS_COL + " TEXT NOT NULL,"
            + SOMA_COL + " TEXT NOT NULL, "
            + RESULTADO_COL + " TEXT NOT NULL, "
            + IMAGEM_COL + " TEXT NOT NULL);";

    public SQLHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}