package com.wprotheus.pdm2atvpratica29jun2024.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyContentProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.wprotheus.pdm2atvpratica29jun2024";
    static final String URL = "content://" + PROVIDER_NAME + "/tb_partida";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    private SQLiteDatabase db_partida;

    @Override
    public boolean onCreate() {
        SQLHelper dbHelper = new SQLHelper(getContext());
        db_partida = dbHelper.getWritableDatabase();
        return db_partida != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = db_partida.query(SQLHelper.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (values == null || !values.containsKey(SQLHelper.RESULTADO_COL) || !values.containsKey(SQLHelper.IMAGEM_COL)) {
            throw new SQLException("Valores necessários não fornecidos." + uri);
        }

        long rowID = db_partida.insert(SQLHelper.TABLE_NAME, null, values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Erro ao gravar dados." + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = db_partida.delete(SQLHelper.TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = db_partida.update(SQLHelper.TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}