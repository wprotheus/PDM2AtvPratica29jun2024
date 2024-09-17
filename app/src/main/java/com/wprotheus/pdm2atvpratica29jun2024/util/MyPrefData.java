package com.wprotheus.pdm2atvpratica29jun2024.util;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPrefData {
    private static final String MYPREF = "Registro_Partidas";
    private static final String NOME_KEY = "nome";
    private static final String QUANTIDADE_KEY = "quantidade";
    private static final String NUMEROS_KEY = "numeros";
    private static final String SOMA_KEY = "soma";
    private static final String RESULTADO_KEY = "resultado";
    private static final String IMAGEM_KEY = "imagem";

    private SharedPreferences shPref;
    private SharedPreferences.Editor shEditor;

    public MyPrefData(Context context) {
        this.shPref = context.getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        this.shEditor = shPref.edit();
    }

    public void salvaNomeQuantidade(String nome, String quantidade) {
        shEditor.putString(NOME_KEY, nome);
        shEditor.putString(QUANTIDADE_KEY, quantidade);
        shEditor.apply();
    }

    public void salvaNomeNumerosSoma(String numeros, int soma) {
        shEditor.putString(NUMEROS_KEY, numeros);
        shEditor.putString(SOMA_KEY, String.valueOf(soma));
        shEditor.apply();
    }

    public void salvaResultadoImagem(String resultado, String imagem) {
        shEditor.putString(RESULTADO_KEY, resultado);
        shEditor.putString(IMAGEM_KEY, imagem);
        shEditor.apply();
    }
}